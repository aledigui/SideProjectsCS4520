package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class InClass03Activity extends AppCompatActivity implements EditProfileFragment.IFragmentUpdate, SelectAvatarFragment.ISelectAvatarFragmentUpdate  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class03);

        // Fragment container
        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerFragment, new EditProfileFragment(), "editDisplayFragment")
                .addToBackStack("editProfile")
                .commit();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerFragment, new SelectAvatarFragment(), "selectAvatarFragment")
                .addToBackStack("selectAvatar")
                .commit();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerFragment, new DisplayFragment(), "displayFragment")
                .addToBackStack("display")
                .commit();
    }

    @Override
    public void sendAvatarImage(int avatarId) {
        DisplayFragment displayFragment = (DisplayFragment) getSupportFragmentManager()
                .findFragmentByTag("displayFragment");
        // method in displayFragment to update the avatar image
        displayFragment.setAvatarImg(avatarId);
    }

    @Override
    public void sendMoodImage(int moodId) {
        DisplayFragment displayFragment = (DisplayFragment) getSupportFragmentManager()
                .findFragmentByTag("displayFragment");
        // method in displayFragment to update the mood image
        displayFragment.setMoodImg(moodId);
    }

    @Override
    public void sendMoodText(String moodText) {
        DisplayFragment displayFragment = (DisplayFragment) getSupportFragmentManager()
                .findFragmentByTag("displayFragment");
        // method in displayFragment to update the mood text
        displayFragment.setMoodText(moodText);
    }

    @Override
    public void sendNameText(String nameText) {
        DisplayFragment displayFragment = (DisplayFragment) getSupportFragmentManager()
                .findFragmentByTag("displayFragment");
        // method in displayFragment to update the name text
        displayFragment.setNameText(nameText);
    }

    @Override
    public void sendEmailText(String emailText) {
        DisplayFragment displayFragment = (DisplayFragment) getSupportFragmentManager()
                .findFragmentByTag("displayFragment");
        // method in displayFragment to update the email text
        displayFragment.setEmailText(emailText);
    }

    @Override
    public void sendRadioGroupText(String rgText) {
        DisplayFragment displayFragment = (DisplayFragment) getSupportFragmentManager()
                .findFragmentByTag("displayFragment");
        // method in displayFragment to update the radio group text
        displayFragment.setRadioGroupText(rgText);
    }

    @Override
    public void sendSelectAvatarImage(int avatarId) {
        EditProfileFragment editProfileFragment = (EditProfileFragment) getSupportFragmentManager()
                .findFragmentByTag("editDisplayFragment");
        editProfileFragment.setAvatarImg(avatarId);
    }
}