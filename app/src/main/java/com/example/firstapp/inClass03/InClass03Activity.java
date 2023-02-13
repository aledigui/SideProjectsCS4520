package com.example.firstapp.inClass03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.firstapp.R;

public class InClass03Activity extends AppCompatActivity implements EditProfileFragment.IFragmentUpdate, SelectAvatarFragment.ISelectAvatarFragmentUpdate, DisplayFragment.IDisplayFragment {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class03);

        // Fragment container
        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerFragment, new EditProfileFragment(), "editDisplayFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sendAvatarImage(int avatarId) {
        getSupportFragmentManager().popBackStack();
        DisplayFragment displayFragment = (DisplayFragment) getSupportFragmentManager()
                .findFragmentByTag("display");
        if (displayFragment != null) {
            displayFragment.setAvatarImg(avatarId);
        }
    }

    @Override
    public void sendMoodImage(int moodId) {
        DisplayFragment displayFragment = (DisplayFragment) getSupportFragmentManager()
                .findFragmentByTag("display");
        // method in displayFragment to update the mood image
        displayFragment.setMoodImg(moodId);
    }

    @Override
    public void sendMoodText(String moodText) {
        DisplayFragment displayFragment = (DisplayFragment) getSupportFragmentManager()
                .findFragmentByTag("display");
        // method in displayFragment to update the mood text
        displayFragment.setMoodText(moodText);
    }

    @Override
    public void sendNameText(String nameText) {
        getSupportFragmentManager().popBackStack();
        DisplayFragment displayFragment = (DisplayFragment) getSupportFragmentManager()
                .findFragmentByTag("display");
        // method in displayFragment to update the name text
        displayFragment.setNameText(nameText);
    }

    @Override
    public void sendEmailText(String emailText) {
        DisplayFragment displayFragment = (DisplayFragment) getSupportFragmentManager()
                .findFragmentByTag("display");
        // method in displayFragment to update the email text
        displayFragment.setEmailText(emailText);
    }

    @Override
    public void sendRadioGroupText(String rgText) {
        DisplayFragment displayFragment = (DisplayFragment) getSupportFragmentManager()
                .findFragmentByTag("display");
        // method in displayFragment to update the radio group text
        displayFragment.setRadioGroupText(rgText);
    }

    @Override
    public void selectAvatarPressed() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerFragment, new SelectAvatarFragment(), "selectAvatar")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSubmitPressed() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerFragment, new DisplayFragment(), "display")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sendSelectAvatarImage(int avatarId) {
        getSupportFragmentManager().popBackStack();
        EditProfileFragment editProfileFragment = (EditProfileFragment) getSupportFragmentManager()
                .findFragmentByTag("editDisplayFragment");
        editProfileFragment.setAvatarImg(avatarId);
    }

    @Override
    public void updateAvatarImg() {
        EditProfileFragment editProfileFragment = (EditProfileFragment) getSupportFragmentManager()
                .findFragmentByTag("editDisplayFragment");
        int avatarId = editProfileFragment.getAvatarImg();
        DisplayFragment displayFragment = (DisplayFragment) getSupportFragmentManager()
                .findFragmentByTag("display");
        displayFragment.setAvatarImg(avatarId);
    }

    @Override
    public void updateNameText() {
        EditProfileFragment editProfileFragment = (EditProfileFragment) getSupportFragmentManager()
                .findFragmentByTag("editDisplayFragment");
        String nameText = editProfileFragment.getNameText();
        DisplayFragment displayFragment = (DisplayFragment) getSupportFragmentManager()
                .findFragmentByTag("display");
        displayFragment.setNameText(nameText);
    }

    @Override
    public void updateEmailText() {
        EditProfileFragment editProfileFragment = (EditProfileFragment) getSupportFragmentManager()
                .findFragmentByTag("editDisplayFragment");
        String emailText = editProfileFragment.getEmailText();
        DisplayFragment displayFragment = (DisplayFragment) getSupportFragmentManager()
                .findFragmentByTag("display");
        displayFragment.setEmailText(emailText);
    }

    @Override
    public void updateRadioChoice() {
        EditProfileFragment editProfileFragment = (EditProfileFragment) getSupportFragmentManager()
                .findFragmentByTag("editDisplayFragment");
        String radioChoice = editProfileFragment.getRadioChoiceButtonId();
        DisplayFragment displayFragment = (DisplayFragment) getSupportFragmentManager()
                .findFragmentByTag("display");
        displayFragment.setRadioGroupText(radioChoice);
    }

    @Override
    public void updateMood() {
        EditProfileFragment editProfileFragment = (EditProfileFragment) getSupportFragmentManager()
                .findFragmentByTag("editDisplayFragment");
        String moodText = editProfileFragment.getMoodText();
        int moodImg = editProfileFragment.getMoodImg();
        DisplayFragment displayFragment = (DisplayFragment) getSupportFragmentManager()
                .findFragmentByTag("display");
        displayFragment.setMoodText(moodText);
        displayFragment.setMoodImg(moodImg);

    }
}