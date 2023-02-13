package com.example.firstapp.a_practiceActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.firstapp.R;

public class PracticeActivity extends AppCompatActivity {

    private Button button_log;
    private Button button_toast;
    public static String TAG = "demo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        setTitle("Practice Activity");
        button_log = findViewById(R.id.button_log);
        button_toast = findViewById(R.id.button_toast);

        button_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Practice!Practice!Practice!!!");
            }
        });
        button_toast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PracticeActivity.this, "Now push to GitHub and Submit!", Toast.LENGTH_LONG).show();
            }
        });
    }
}