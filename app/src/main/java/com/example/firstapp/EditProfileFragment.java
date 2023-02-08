package com.example.firstapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {
    // parameter arguments
    private static final String ARG_AVATARID = "avatarImageFragment";
    private static final String ARG_MOODID = "moodFragmentImage";
    private static final String ARG_MOODTEXT = "moodFragmentTextVary";
    private static final String ARG_ANDROIDIOSCHOICE = "androidIosChoice";
    // UI elements
    private EditText nameInputFragment;
    private EditText emailInputFragment;
    private ImageView avatarImageFragment;
    private RadioButton androidFragmentRd;
    private RadioButton iosFragmentRd;
    private TextView moodFragmentTextVary;
    private SeekBar moodSeekbar;
    private ImageView moodFragmentImage;
    private Button submitFragmentButton;

    private RadioGroup choiceRadioGroup;

    private View mainView;

    private Boolean avatarFlag = false;

    private String radioChoice = "null";

    public EditProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param avatarID the avatar image id.
     * @param moodId the mood image id.
     * @param moodText the mood text.
     * @param androidIosChoice the android/iOS radio choice..
     * @return A new instance of fragment EditProfileFragment.
     */
    public static EditProfileFragment newInstance(int avatarID, int moodId, String moodText, String androidIosChoice) {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        // is this correct?
        args.putInt(ARG_AVATARID, avatarID);
        args.putInt(ARG_MOODID, moodId);
        args.putString(ARG_MOODTEXT, moodText);
        args.putString(ARG_ANDROIDIOSCHOICE, androidIosChoice);
        fragment.setArguments(args);
        return fragment;
    }

    IFragmentUpdate updateData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView =  inflater.inflate(R.layout.fragment_edit_profile, container, false);

        // Find the elements
        nameInputFragment = mainView.findViewById(R.id.nameInputFragment);
        emailInputFragment = mainView.findViewById(R.id.emailInputFragment);
        avatarImageFragment = mainView.findViewById(R.id.avatarImageFragment);
        androidFragmentRd = mainView.findViewById(R.id.androidFragmentRd);
        iosFragmentRd = mainView.findViewById(R.id.iosFragmentRd);
        moodFragmentTextVary = mainView.findViewById(R.id.moodFragmentTextVary);
        moodSeekbar = mainView.findViewById(R.id.moodSeekbar);
        moodFragmentImage = mainView.findViewById(R.id.moodFragmentImage);
        submitFragmentButton = mainView.findViewById(R.id.submitFragmentButton);

        // example:
        if (avatarFlag) {
            updateData.sendAvatarImage(avatarImageFragment.getId());
        } else {
            Toast.makeText(getActivity(),
                    "Please select an avatar image",
                    Toast.LENGTH_LONG).show();
        }
        if (!radioChoice.equals("null")) {
            updateData.sendMoodText(radioChoice);
        } else {

        }
        if (nameInputFragment.getText() != null) {
            char[] tempChar = nameInputFragment.getText().toString().toCharArray();
            if (tempChar.length == 0) {
                Toast.makeText(getActivity(),
                        "Invalid name - only characters allowed",
                        Toast.LENGTH_LONG).show();
            } else {
                for(char c : tempChar) {
                    if (!Character.isLetter(c) && c != ' ') {
                        Toast.makeText(getActivity(),
                                "Invalid name - only characters allowed",
                                Toast.LENGTH_LONG).show();
                        // return mainView;
                        break;
                    }
                }
            }
        } else {
            updateData.sendNameText(nameInputFragment.getText().toString());
        }
        String emailFormat = "^[A-Za-z]*@[A-Za-z]+(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailFormat);
        if (emailInputFragment != null) {
            if (!pattern.matcher(emailInputFragment.getText().toString()).matches()) {
                Toast.makeText(getActivity(),
                        "Invalid email",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            updateData.sendEmailText(emailInputFragment.getText().toString());
        }
        return mainView;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IFragmentUpdate) {
            updateData = (IFragmentUpdate) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement IFragmentUpdate");
        }
    }

    // getter for the avatar image id
    public ImageView getAvatarImg() {
        return avatarImageFragment;
    }

    // setter for the avatar image id
    public void setAvatarImg(int avatarImgId) {
        avatarFlag = true;
        avatarImageFragment.setImageResource(avatarImgId);
    }

    // getter for the mood image id
    public int getMoodImg() {
        return moodFragmentImage.getId();
    }

    // setter for the mood image id
    public void setMoodImgId(int moodImgId) {
        moodFragmentImage.setImageResource(moodImgId);
    }

    // getter for the mood text
    public String getMoodText() {
        return moodFragmentTextVary.getText().toString();
    }

    // setter for the mood text
    public void setMoodText(String moodText) {
        radioChoice = moodText;
        moodFragmentTextVary.setText(moodText);
    }

    // getter for the radio group choice between iOS or Android
    public int getRadioChoiceButtonId() {
        return choiceRadioGroup.getCheckedRadioButtonId();
    }

    public interface IFragmentUpdate {
        void sendAvatarImage (int avatarId);
        void sendMoodImage(int moodId);
        void sendMoodText(String moodText);

        void sendNameText(String nameText);

        void sendEmailText(String emailText);

        void sendRadioGroupText(String rgText);
    }

}