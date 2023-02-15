package com.example.firstapp.inClass04;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class HeavyWork implements Runnable{
    private int iterations;

    static final int COUNT = 9000000;

    static ArrayList<Double> returnArray;


    private Handler returnValueHandler;

    public final static String RANDOM_GENERATOR = "GENERATING!";

    public final static String PERCENTAGE = "PERCENTAGE";

    public final static int PROGRESS = 0x007;

    // constructor with the passed in complexity iterations from the inClass04activity
    public HeavyWork (int complexInt, Handler sentHandler) {
        this.iterations = complexInt;
        this.returnValueHandler = sentHandler;
    }

    // run method
    @Override
    public void run() {
        returnArray = new ArrayList<>();

        for (int i=0; i<iterations; i++){
            double tempRandomInt = getNumber();
            returnArray.add(tempRandomInt);

            // handling sending data to the main activity from thread
            Message valuesMessage = new Message();

            // inserting data into bundle for the values and progress bar
            Bundle doubleBundle = new Bundle();
            doubleBundle.putDouble(RANDOM_GENERATOR, tempRandomInt);
            doubleBundle.putInt(PERCENTAGE, i+1);

            // actually sending the data back
            valuesMessage.what = PROGRESS;
            valuesMessage.setData(doubleBundle);
            returnValueHandler.sendMessage(valuesMessage);
        }
    }

    static double getNumber(){
        double num = 0;
        Random ran = new Random();
        for(int i=0;i<COUNT; i++){
            num = num + (Math.random()*ran.nextDouble()*100+ran.nextInt(50))*1000;
        }
        return num / ((double) COUNT);
    }

    // public static void main(String[] args) {
    //     ArrayList<Double> arrayList = new ArrayList<>();
    //     arrayList = getArrayNumbers(200);
    //     for(double num: arrayList){
    //         System.out.println(num);
    //     }
    // }
}