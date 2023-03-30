package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

import com.example.firstapp.inClass01.inClass01Activity;
import com.example.firstapp.inClass02.inClass02Activity;
import com.example.firstapp.inClass03.InClass03Activity;
import com.example.firstapp.a_practiceActivity.PracticeActivity;
import com.example.firstapp.inClass04.InClass04Activity;
import com.example.firstapp.inClass05.InClass05Activity;
import com.example.firstapp.inClass06.InClass06Activity;
import com.example.firstapp.inClass08.RegisterSignUp;

public class MainActivity extends AppCompatActivity {

    private Button buttonPractice;
    private Button button_inclass01;

    private Button button_inclass02;

    private Button button_inclass03;

    private Button button_inclass04;

    private Button button_inclass05;

    private Button button_inclass06;

    private Button button_inclass08;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonPractice = findViewById(R.id.buttonPractice);
        button_inclass01 = findViewById(R.id.button_inclass01);
        button_inclass02 = findViewById(R.id.button_inclass02);
        button_inclass03 = findViewById(R.id.button_inclass03);
        button_inclass04 = findViewById(R.id.button_inclass04);
        button_inclass05 = findViewById(R.id.button_inclass05);
        button_inclass06 = findViewById(R.id.button_inclass06);
        button_inclass08 = findViewById(R.id.button_inclass08);

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

        button_inclass03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass03Activity = new Intent(MainActivity.this, InClass03Activity.class);
                startActivity(toInClass03Activity);
            }
        });

        button_inclass04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass04Activity = new Intent(MainActivity.this, InClass04Activity.class);
                startActivity(toInClass04Activity);
            }
        });

        button_inclass05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass05Activity = new Intent(MainActivity.this, InClass05Activity.class);
                startActivity(toInClass05Activity);
            }
        });

        button_inclass06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass06Activity = new Intent(MainActivity.this, InClass06Activity.class);
                startActivity(toInClass06Activity);
            }
        });

        button_inclass08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass08Activity = new Intent(MainActivity.this, RegisterSignUp.class);
                startActivity(toInClass08Activity);
            }
        });
    }
}