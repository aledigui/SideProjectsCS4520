package com.example.firstapp.inClass02;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstapp.R;

import java.util.Objects;
import java.util.regex.Pattern;

public class inClass02Activity extends AppCompatActivity {
    private Button submit_button;
    private ImageView avatar_image;

    private EditText name_textInput;

    private EditText email_textInput;

    private ImageView mood_img;

    final static String Profile_Key = "profile_key";

    private Boolean profileTag;

    private TextView moodText;

    private RadioGroup radioGroup;

    private String rgChoice = "null";

    private SeekBar mood_seekbar;

    final String TAG = "demo";

    int avatar_id = 0;

    int mood_id = 0;

    final static int RESULT_OK = 8;

    ActivityResultLauncher<Intent> startActivityForResult
            = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Bundle imageBundle = result.getData().getExtras();
                int imageBmp = imageBundle.getInt("imageAvatar");
                avatar_image.setImageResource(imageBmp);
                avatar_id = imageBmp;
            }
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class02);
        profileTag = false;

        avatar_image = findViewById(R.id.avatar_image);
        submit_button = findViewById(R.id.submit_button);
        name_textInput = findViewById(R.id.name_textInput);
        email_textInput = findViewById(R.id.email_textInput);
        mood_img = findViewById(R.id.mood_img);
        moodText = findViewById(R.id.moodText);
        radioGroup = findViewById(R.id.radioGroup);
        mood_seekbar = findViewById(R.id.mood_seekbar);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.android_radio) {
                    rgChoice = "Android";
                } else if (i == R.id.ios_radio) {
                    rgChoice = "iOS";
                }
            }
        });

        // seekbar logic (4 moods: 0-25/ 26-50 / 51-75 / 76-100)
        mood_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (0 <= i && i <= 25) {
                    mood_img.setImageResource(R.drawable.angry);
                    mood_id = R.drawable.angry;
                    moodText.setText("Angry");
                } else if (26 <= i && i <= 50) {
                    mood_img.setImageResource(R.drawable.sad);
                    mood_id = R.drawable.sad;
                    moodText.setText("Sad");
                } else if (51 <= i && i <= 75) {
                    mood_img.setImageResource(R.drawable.happy);
                    mood_id = R.drawable.happy;
                    moodText.setText("Happy");
                } else if (75 < i && i <= 100) {
                    mood_img.setImageResource(R.drawable.awesome);
                    mood_id = R.drawable.awesome;
                    moodText.setText("Awesome");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass02ProfileActivity = new Intent(inClass02Activity.this, inClass02_profile.class);
                // pass to the last screen the necessary extras
                if (name_textInput != null) {
                    char[] tempChar = name_textInput.getText().toString().toCharArray();
                    if (tempChar.length == 0) {
                        Toast.makeText(inClass02Activity.this,
                                "Invalid name - only characters allowed",
                                Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        for(char c : tempChar) {
                            if (!Character.isLetter(c) && c != ' ') {
                                Toast.makeText(inClass02Activity.this,
                                        "Invalid name - only characters allowed",
                                        Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                    }
                }
                String emailFormat = "^[A-Za-z]*@[A-Za-z]+(\\.[A-Za-z]{2,})$";
                Pattern pattern = Pattern.compile(emailFormat);
                if (email_textInput != null) {
                    if (!pattern.matcher(email_textInput.getText().toString()).matches()) {
                        Toast.makeText(inClass02Activity.this,
                                "Invalid email",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                if (avatar_id == 0) {
                    Toast.makeText(inClass02Activity.this,
                            "Please select an avatar",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if (Objects.equals(rgChoice, "null")) {
                    Toast.makeText(inClass02Activity.this,
                            "Please select either Android or iOS",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                // just in case there is an error and no mood is selected
                if (mood_id == 0) {
                    Toast.makeText(inClass02Activity.this,
                            "Please select a mood",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                //parcelable
                profile profile = new profile(avatar_id,
                        name_textInput.getText().toString(),
                        email_textInput.getText().toString(),
                        rgChoice, moodText.getText().toString(),
                        mood_id);
                // intent
                toInClass02ProfileActivity.putExtra(Profile_Key, profile);
                startActivity(toInClass02ProfileActivity);
            }
        });
        avatar_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass02AvatarActivity = new Intent(inClass02Activity.this, inClass02_avatar.class);
                startActivityForResult.launch(toInClass02AvatarActivity);
            }
        });
    }
}