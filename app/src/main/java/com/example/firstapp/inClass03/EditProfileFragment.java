package com.example.firstapp.inClass03;

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

import com.example.firstapp.R;
import com.example.firstapp.inClass02.inClass02Activity;

import java.util.Objects;
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

    private int mood_id = 0;

    private String radioChoice = "null";

    private Boolean subnmitFlag = false;

    private int avatarId = 0;

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
        getActivity().setTitle("Edit Profile Activity");

        // Find the elements
        nameInputFragment = mainView.findViewById(R.id.nameInputFragment);
        emailInputFragment = mainView.findViewById(R.id.emailInputFragment);
        avatarImageFragment = mainView.findViewById(R.id.avatarImageFragment);
        choiceRadioGroup = mainView.findViewById(R.id.choiceRadioGroup);
        androidFragmentRd = mainView.findViewById(R.id.androidFragmentRd);
        iosFragmentRd = mainView.findViewById(R.id.iosFragmentRd);
        moodFragmentTextVary = mainView.findViewById(R.id.moodFragmentTextVary);
        moodSeekbar = mainView.findViewById(R.id.moodSeekbar);
        moodFragmentImage = mainView.findViewById(R.id.moodFragmentImage);
        submitFragmentButton = mainView.findViewById(R.id.submitFragmentButton);


        avatarImageFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData.selectAvatarPressed();
            }
        });

        // checks for the choice made in the radio group
        choiceRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.androidFragmentRd) {
                    radioChoice = "Android";
                    subnmitFlag = true;
                } else if (i == R.id.iosFragmentRd) {
                    radioChoice = "iOS";
                    subnmitFlag = true;
                }
            }
        });
        moodSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (0 <= i && i <= 25) {
                    moodFragmentImage.setImageResource(R.drawable.angry);
                    mood_id = R.drawable.angry;
                    moodFragmentTextVary.setText("Angry");
                } else if (26 <= i && i <= 50) {
                    moodFragmentImage.setImageResource(R.drawable.sad);
                    mood_id = R.drawable.sad;
                    moodFragmentTextVary.setText("Sad");
                } else if (51 <= i && i <= 75) {
                    moodFragmentImage.setImageResource(R.drawable.happy);
                    mood_id = R.drawable.happy;
                    moodFragmentTextVary.setText("Happy");
                } else if (75 < i && i <= 100) {
                    moodFragmentImage.setImageResource(R.drawable.awesome);
                    mood_id = R.drawable.awesome;
                    moodFragmentTextVary.setText("Awesome");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // nothing
            }
        });

        submitFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // email format
                String emailFormat = "^[A-Za-z]*@[A-Za-z]+(\\.[A-Za-z]{2,})$";
                Pattern pattern = Pattern.compile(emailFormat);

                // name check
                if (nameInputFragment.getText() != null) {
                    char[] tempChar = nameInputFragment.getText().toString().toCharArray();
                    if (tempChar.length == 0) {
                        Toast.makeText(getActivity(),
                                "Invalid name - only characters allowed",
                                Toast.LENGTH_LONG).show();
                        subnmitFlag = false;
                        return;
                    } else {
                        for(char c : tempChar) {
                            if (!Character.isLetter(c) && c != ' ') {
                                Toast.makeText(getActivity(),
                                        "Invalid name - only characters allowed",
                                        Toast.LENGTH_LONG).show();
                                subnmitFlag = false;
                                return;
                            }
                        }
                    }
                } else {
                    subnmitFlag = true;
                }

                // email check
                if (emailInputFragment != null) {
                    if (!pattern.matcher(emailInputFragment.getText().toString()).matches()) {
                        Toast.makeText(getActivity(),
                                "Invalid email",
                                Toast.LENGTH_LONG).show();
                        subnmitFlag = false;
                        return;
                    }
                } else {
                    subnmitFlag = true;
                }

                // avatar image check
                if (avatarId == 0){
                    Toast.makeText(getActivity(),
                            "Please select an avatar image",
                            Toast.LENGTH_LONG).show();
                    subnmitFlag = false;
                    return;
                } else {
                    subnmitFlag = true;
                }
                // radio choice
                if (choiceRadioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getActivity(), "Please select either Android or iOS", Toast.LENGTH_LONG).show();
                    subnmitFlag = false;
                    return;
                } else {
                    subnmitFlag = true;
                }
                // just in case there is an error and no mood is selected
                if (mood_id == 0) {
                    Toast.makeText(getActivity(),
                            "Please select a mood",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                // subnmitFlag is just another source of checking in case of errors.
                if (subnmitFlag) {
                       updateData.onSubmitPressed();
                }
            }
        });

        return mainView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (avatarId != 0) {
            avatarImageFragment.setImageResource(avatarId);
        }
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
    public int getAvatarImg() {
        return avatarId;
    }

    // setter for the avatar image id
    public void setAvatarImg(int avatarImgId) {
        avatarId = avatarImgId;
        avatarImageFragment.setImageResource(avatarImgId);
    }

    // getter for the mood image id
    public int getMoodImg() {
        return mood_id;
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
    public String getRadioChoiceButtonId() {
        return radioChoice;
    }

    // getter for the name text
    public String getNameText() {
        return nameInputFragment.getText().toString();
    }

    // getter for the email text
    public String getEmailText() {
        return emailInputFragment.getText().toString();
    }

    public interface IFragmentUpdate {
        void sendAvatarImage (int avatarId);
        void sendMoodImage(int moodId);
        void sendMoodText(String moodText);

        void sendNameText(String nameText);

        void sendEmailText(String emailText);

        void sendRadioGroupText(String rgText);

        void selectAvatarPressed();

        void onSubmitPressed();
    }

}