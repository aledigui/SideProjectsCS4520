package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class inClass02_avatar extends AppCompatActivity {

    private ImageView imageAvatar_l1;
    private ImageView imageAvatar_l2;
    private ImageView imageAvatar_l3;
    private ImageView imageAvatar_r1;
    private ImageView imageAvatar_r2;
    private ImageView imageAvatar_r3;

    final static  String Image_Key = "imageAvatar";

    final static int RESULT_OK = 8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class02_avatar);
        imageAvatar_l1 = findViewById(R.id.imageAvatar_l1);
        imageAvatar_l2 = findViewById(R.id.imageAvatar_l2);
        imageAvatar_l3 = findViewById(R.id.imageAvatar_l3);
        imageAvatar_r1 = findViewById(R.id.imageAvatar_r1);
        imageAvatar_r2 = findViewById(R.id.imageAvatar_r2);
        imageAvatar_r3 = findViewById(R.id.imageAvatar_r3);

        imageAvatar_l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass02Activity = new Intent();
                toInClass02Activity.putExtra(Image_Key, R.drawable.avatar_f_1);
                setResult(RESULT_OK, toInClass02Activity);
                finish();
            }
        });
        imageAvatar_l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass02Activity = new Intent(inClass02_avatar.this, inClass02Activity.class);
                toInClass02Activity.putExtra(Image_Key, R.drawable.avatar_f_2);
                setResult(RESULT_OK, toInClass02Activity);
                finish();
            }
        });
        imageAvatar_l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass02Activity = new Intent(inClass02_avatar.this, inClass02Activity.class);
                toInClass02Activity.putExtra(Image_Key, R.drawable.avatar_f_3);
                setResult(RESULT_OK, toInClass02Activity);
                finish();
            }
        });
        imageAvatar_r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass02Activity = new Intent(inClass02_avatar.this, inClass02Activity.class);
                toInClass02Activity.putExtra(Image_Key, R.drawable.avatar_m_3);
                setResult(RESULT_OK, toInClass02Activity);
                finish();
            }
        });
        imageAvatar_r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass02Activity = new Intent(inClass02_avatar.this, inClass02Activity.class);
                toInClass02Activity.putExtra(Image_Key, R.drawable.avatar_m_2);
                setResult(RESULT_OK, toInClass02Activity);
                finish();
            }
        });
        imageAvatar_r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass02Activity = new Intent(inClass02_avatar.this, inClass02Activity.class);
                toInClass02Activity.putExtra(Image_Key, R.drawable.avatar_m_3);
                setResult(RESULT_OK, toInClass02Activity);
                finish();
            }
        });
    }
}