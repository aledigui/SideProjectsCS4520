package com.example.firstapp.inClass01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.firstapp.R;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class inClass01Activity extends AppCompatActivity {
    public static String TAG = "demo";
    private Button buttonCalculate;
    private EditText weight_input;
    private EditText feet_input;
    private EditText inches_input;
    private static final DecimalFormat df = new DecimalFormat("0.0");

    private TextView bmi_results;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class01);

        buttonCalculate = findViewById(R.id.button_calculate);
        weight_input = findViewById(R.id.weight_input);
        feet_input = findViewById(R.id.feet_input);
        inches_input = findViewById(R.id.inches_input);
        bmi_results = findViewById(R.id.bmi_results);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check for non-numerical inputs
                try {
                    double d = Double.parseDouble(weight_input.getText().toString());
                    double d1 = Double.parseDouble(feet_input.getText().toString());
                    double d2 = Double.parseDouble(inches_input.getText().toString());
                } catch (NumberFormatException nfe) {
                    Toast.makeText(inClass01Activity.this, "Invalid inputs", Toast.LENGTH_LONG).show();
                    return;
                }
                // check for negative inputs
                if (Double.parseDouble(weight_input.getText().toString()) < 0
                        || Double.parseDouble(feet_input.getText().toString()) < 0
                || Double.parseDouble(inches_input.getText().toString()) < 0) {
                    Toast.makeText(inClass01Activity.this, "Invalid inputs", Toast.LENGTH_LONG).show();
                } else {
                    // get the total inches
                    double inches = (Integer.parseInt(feet_input.getText().toString()) * 12)
                            + Integer.parseInt(inches_input.getText().toString());
                    // BMI = (Weight in Pounds / (Height in inches x Height in inches)) x 703
                    double bmi = (Integer.parseInt(weight_input.getText().toString())
                            / (inches * inches)) * 703;
                    // format BMI result into 0.0 form
                    df.setRoundingMode(RoundingMode.UP);
                    bmi = Double.parseDouble(df.format(bmi));
                    Log.d(TAG, String.valueOf(bmi));
                    if (bmi < 18.5) {
                        // output you are underweight
                        bmi_results.setText("Your BMI: " + bmi + "\n You are Underweight");
                    } else if (18.5 <= bmi && bmi <= 24.9) {
                        // output you are normal weight
                        bmi_results.setText("Your BMI: " + bmi + "\n You are Normal Weight");
                    } else if (25 <= bmi && bmi <= 29.9) {
                        // output you are overrweight
                        bmi_results.setText("Your BMI: " + bmi + "\n You are Overweight");
                    } else if (bmi >= 30) {
                        // output you are obese
                        bmi_results.setText("Your BMI: " + bmi + "\n You are Obese");
                    }
                    // toast
                    Toast.makeText(inClass01Activity.this, "BMI calculated", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}