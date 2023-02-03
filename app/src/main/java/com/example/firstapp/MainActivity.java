package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Button buttonPractice;
    private Button button_inclass01;

    private Button button_inclass02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonPractice = findViewById(R.id.buttonPractice);
        button_inclass01 = findViewById(R.id.button_inclass01);
        button_inclass02 = findViewById(R.id.button_inclass02);

        buttonPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPracticeActivity = new Intent(MainActivity.this, PracticeActivity.class);
                startActivity(toPracticeActivity);
            }
        });

        button_inclass01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass01Activity = new Intent(MainActivity.this, inClass01Activity.class);
                startActivity(toInClass01Activity);
            }
        });

        button_inclass02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass02Activity = new Intent(MainActivity.this, inClass02Activity.class);
                startActivity(toInClass02Activity);
            }
        });
    }
}