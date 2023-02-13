package com.example.firstapp.inClass02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firstapp.R;

public class inClass02_profile extends AppCompatActivity {
    private ImageView avatar_profile;

    private TextView moodIntent;

    private TextView intentName;

    private TextView intentEmail;

    private TextView softwareIntent;

    private ImageView mood_img_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class02_profile);

        avatar_profile = findViewById(R.id.avatar_profile);
        moodIntent = findViewById(R.id.moodIntent);
        intentName = findViewById(R.id.intentName);
        intentEmail = findViewById(R.id.intentEmail);
        mood_img_profile = findViewById(R.id.mood_img_profile);
        moodIntent = findViewById(R.id.moodIntent);
        softwareIntent = findViewById(R.id.softwareIntent);

        if (getIntent() != null && getIntent().getExtras() != null) {
            // Getting the parcelable
            profile profile = getIntent().getParcelableExtra(inClass02Activity.Profile_Key);
            // setting the texts
            intentName.setText(profile.intentName);
            intentEmail.setText(profile.intentEmail);
            moodIntent.setText(profile.moodIntent);
            softwareIntent.setText(profile.softwareIntent);

            avatar_profile.setImageResource(profile.avatar_profile);
            mood_img_profile.setImageResource(profile.mood_img_profile);

        }
    }
}