package com.example.firstapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayFragment extends Fragment {

    private static final String ARG_AVATAR = "avatar_id";
    private static final String ARG_MOODIMG = "mood_img";
    private static final String ARG_MOODTXT = "mood_txt";
    private static final String ARG_NAME = "name";
    private static final String ARG_EMAIL = "email";
    private static final String ARG_RADIOGROUP = "radio_choice";

    // TODO: Rename and change types of parameters
    private ImageView avatarImg;
    private ImageView moodImg;
    private TextView moodText;
    private TextView nameText;
    private TextView emailText;
    private TextView radioGroupText;

    private View displayView;

    public DisplayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param avatarId avatar image id.
     * @param moodId mood image id.
     * @param nameString name of the user.
     * @param emailString email of the user.
     * @param moodString mood of the user.
     * @param radioString radio group choice of the user.
     *
     * @return A new instance of fragment DisplayFragment.
     */
    public static DisplayFragment newInstance(int avatarId, int moodId, String nameString,
                                              String emailString, String radioString, String moodString) {
        DisplayFragment fragment = new DisplayFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_AVATAR, avatarId);
        args.putInt(ARG_MOODIMG,  moodId);
            args.putString(ARG_MOODTXT,  moodString);
        args.putString(ARG_NAME,  nameString);
        args.putString(ARG_EMAIL,  emailString);
        args.putString(ARG_RADIOGROUP,  radioString);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: DO THIS!
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        displayView = inflater.inflate(R.layout.fragment_display, container, false);

        avatarImg = displayView.findViewById(R.id.finalFragmentAvatar);
        moodImg = displayView.findViewById(R.id.moodFinalImg);
        moodText = displayView.findViewById(R.id.moodFinalFragmentText);
        nameText = displayView.findViewById(R.id.nameFragmentFinal);
        emailText = displayView.findViewById(R.id.emailFragmentFinal);
        radioGroupText = displayView.findViewById(R.id.finalRadioButtonChoice);

        return displayView;
    }

    // sets the avatar image id with the passed id from fragment 1 via the main activity
    public void setAvatarImg(int avatarSent) {
        avatarImg.setImageResource(avatarSent);
    }

    // sets the mood image id with the passed id from fragment 1 via the main activity
    public void setMoodImg(int moodImgSent) {
        moodImg.setImageResource(moodImgSent);
    }

    // sets the name text with the passed String from fragment 1 via the main activity
    public void setNameText(String nameSent) {
        nameText.setText(nameSent);
    }

    // sets the email text with the passed String from fragment 1 via the main activity
    public void setEmailText(String emailSent) {
        emailText.setText(emailSent);
    }

    // sets the radio group text with the passed String from fragment 1 via the main activity
    public void setRadioGroupText(String rgSent) {
        radioGroupText.setText(rgSent);
    }

    // sets the mood text with the passed String from fragment 1 via the main activity
    public void setMoodText(String moodSent) {
        moodText.setText(moodSent);
    }


}