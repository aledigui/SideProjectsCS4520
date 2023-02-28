package com.example.firstapp.inClass04;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstapp.R;
import com.example.firstapp.inClass02.inClass02Activity;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InClass04Activity extends AppCompatActivity {

    private TextView complexityInt;

    private SeekBar complexSeekBar;

    private TextView timesText;

    private TextView minimumInt;

    private TextView maximumInt;

    private TextView avgInt;

    private ProgressBar progressBar;

    private Button generateButton;

    private int complexityFlag = 0;

    private ExecutorService threadPool;

    private Handler returnHandler;

    private ArrayList<Double> valuesArray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class04);
        
        complexityInt = findViewById(R.id.complexityInt);
        minimumInt = findViewById(R.id.minimumInt);
        maximumInt = findViewById(R.id.maximumInt);
        avgInt = findViewById(R.id.avgInt);
        progressBar = findViewById(R.id.progressBar);
        generateButton = findViewById(R.id.generateButton);
        complexSeekBar = findViewById(R.id.complexSeekBar);
        timesText = findViewById(R.id.timesText);

        threadPool = Executors.newFixedThreadPool(1);

        complexSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // set the complexity integer to the chosen i times through the seekbar
                if (i == 1) {
                    timesText.setText("time");
                } else {
                    timesText.setText("times");
                }
                complexityInt.setText(String.valueOf(i));
                complexityFlag = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // nothing
            }
        });

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (complexityFlag == 0) {
                    // do toast alert
                    Toast.makeText(InClass04Activity.this,
                            "Invalid complexity - select a number bigger than 0",
                            Toast.LENGTH_LONG).show();
                    return;
                } else  {
                    progressBar.setMax(complexityFlag);
                    threadPool.execute(new HeavyWork(complexityFlag, returnHandler));
                }
            }
        });

        returnHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                if (message.what == HeavyWork.PROGRESS) {
                    Bundle receivedDouble = message.getData();
                    double valueTemp = receivedDouble.getDouble(HeavyWork.RANDOM_GENERATOR);
                    int progressPercent = receivedDouble.getInt(HeavyWork.PERCENTAGE);
                    System.out.println(String.valueOf(progressPercent));
                    progressBar.setProgress(progressPercent);
                    valuesArray.add(valueTemp);
                }

                // setting the values
                if (progressBar.getProgress() == complexityFlag) {
                    // setting the intial value to the first value of the array list
                    double minTempVal = valuesArray.get(0);
                    // setting the max initial value to 0 since there will be bigger numbers
                    double maxTempVal = 0.0;
                    double avgVal = 0.0;
                    for (int i=0; i<valuesArray.size(); i++) {
                        // finding the smallest or largest
                        if (minTempVal > valuesArray.get(i)) {
                            minTempVal = valuesArray.get(i);
                        } else if (maxTempVal < valuesArray.get(i)) {
                            maxTempVal = valuesArray.get(i);
                        }
                        avgVal += valuesArray.get(i);
                    }

                    // calculating the avergae
                    avgVal = avgVal/complexityFlag;

                    // setting the minimum value
                    minimumInt.setText(String.valueOf(minTempVal));

                    // setting the maximum value
                    maximumInt.setText(String.valueOf(maxTempVal));

                    // setting the average value
                    avgInt.setText(String.valueOf(avgVal));
                }
                return false;
            }
        });
    }
}