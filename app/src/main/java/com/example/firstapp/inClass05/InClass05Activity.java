package com.example.firstapp.inClass05;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstapp.inClass04.InClass04Activity;
import com.squareup.picasso.Picasso;

import com.example.firstapp.R;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class InClass05Activity extends AppCompatActivity {

    private EditText imgInputText;
    private Button buttonGo;
    private ImageView previousImg;
    private ImageView nextImg;

    private ImageView imageResult;

    private TextView loadingText;

    private String keyWord = "";

    private String[] imageDownload;

    private Boolean keywordFlag = false;

    private String[] splitKeywords;

    private int imageCounter = 0;

    private ProgressBar loadingBar;

    private boolean arrowsAllowed = false;

    private boolean isInternetAvailable() {
        InetAddress inetAddress = null;
        try {
            Future<InetAddress> future = Executors.newSingleThreadExecutor().submit(new Callable<InetAddress>() {
                @Override
                public InetAddress call() {
                    try {
                        return InetAddress.getByName("google.com");
                    } catch (UnknownHostException e) {
                        return null;
                    }
                }
            });
            inetAddress = future.get(1000, TimeUnit.MILLISECONDS);
            future.cancel(true);
        } catch (ExecutionException | TimeoutException | InterruptedException e) {
        }
        return inetAddress != null && !inetAddress.equals("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class05);

        imgInputText = findViewById(R.id.imgInputText);
        buttonGo = findViewById(R.id.buttonGo);
        previousImg = findViewById(R.id.previousImg);
        nextImg = findViewById(R.id.nextImg);
        loadingText = findViewById(R.id.loadingText);
        imageResult = findViewById(R.id.imageResult);
        loadingBar = findViewById(R.id.loadingBar);

        // setting as invisible to begin with
        loadingBar.setVisibility(View.INVISIBLE);

        // client
        OkHttpClient client = new OkHttpClient();
        // url for the keyword
        Request keywordRequest = new Request.Builder()
                .url("http://ec2-54-164-201-39.compute-1.amazonaws.com/apis/\n" +
                        "images/keywords\n")
                .build();

        if (isInternetAvailable()) {
            // call to get the keywords
            client.newCall(keywordRequest).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        runOnUiThread(new Runnable() {
                            ResponseBody responseBody = response.body();
                            String keywords = responseBody.string();

                            @Override
                            public void run() {
                                // gettting the different keywords
                                splitKeywords = keywords.split(",");
                            }
                        });

                    }
                }
            });
        }



        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInternetAvailable()) {
                    String keyword = imgInputText.getText().toString();

                    // check if the keyword is correct
                    for (String s : splitKeywords) {
                        if (!s.equals(keyword)) {
                            keywordFlag = false;
                        }
                        if (s.equals(keyword)) {
                            keywordFlag = true;
                            break;
                        }
                    }

                    if (!keywordFlag) {
                        Toast.makeText(InClass05Activity.this, "Invalid keyword!", Toast.LENGTH_LONG).show();
                        return;
                    }

                    // url for the images with keyword passed in from main activity
                    HttpUrl imageUrl = HttpUrl.parse("http://ec2-54-164-201-39.compute-1.amazonaws.com/apis/\n"
                            + "images/retrieve\n")
                            .newBuilder().addQueryParameter("keyword", keyword)
                            .build();


                    // request for the images urls
                    Request imgRequest = new Request.Builder().url(imageUrl).build();

                    // call request to get the images linked to the keyword
                    client.newCall(imgRequest).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if (response.isSuccessful()) {
                                ResponseBody responseBody = response.body();
                                String keywordImg = responseBody.string();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {


                                        if (keywordImg.equals("")) {
                                            Toast.makeText(InClass05Activity.this, "No Images Found.", Toast.LENGTH_LONG).show();
                                        } else {
                                            // splitting the image string
                                            imageDownload = keywordImg.split("\n");
                                            arrowsAllowed = true;
                                            // loading logic
                                            loadingBar.setVisibility(View.VISIBLE);
                                            loadingText.setText("loading...");
                                            // downloading the img and setting it to the imageResult
                                            Picasso.get().load(imageDownload[0]).into(imageResult);
                                            loadingBar.setVisibility(View.INVISIBLE);
                                            loadingText.setText("");
                                        }

                                    }
                                });
                            }

                        }
                    });
                } else {
                    Toast.makeText(InClass05Activity.this,
                            "No internet connection.",
                            Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        nextImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrowsAllowed) {
                    // user is one the last image and clicks next needs to go back to first
                    if (imageCounter == (imageDownload.length - 1)) {
                        // next image is the first
                        imageCounter = 0;
                        // loading logic
                        loadingBar.setVisibility(View.VISIBLE);
                        loadingText.setText("loading next...");
                        // downloading the img and setting it to the imageResult
                        Picasso.get().load(imageDownload[imageCounter]).into(imageResult);
                        // stop loading
                        loadingBar.setVisibility(View.INVISIBLE);
                        loadingText.setText("");
                    } else {
                        // next image
                        imageCounter += 1;
                        // loading logic
                        loadingBar.setVisibility(View.VISIBLE);
                        loadingText.setText("loading next...");
                        // downloading the img and setting it to the imageResult
                        Picasso.get().load(imageDownload[imageCounter]).into(imageResult);
                        // stop loading
                        loadingBar.setVisibility(View.INVISIBLE);
                        loadingText.setText("");
                    }
                }
            }
        });

        previousImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrowsAllowed) {
                    // user is one the last image and clicks next needs to go back to first
                    if (imageCounter == 0) {
                        // next image is the last
                        imageCounter = (imageDownload.length - 1);
                        // loading logic
                        loadingBar.setVisibility(View.VISIBLE);
                        loadingText.setText("loading previous...");
                        // downloading the img and setting it to the imageResult
                        Picasso.get().load(imageDownload[imageCounter]).into(imageResult);
                        // stop loading
                        loadingBar.setVisibility(View.INVISIBLE);
                        loadingText.setText("");
                    } else {
                        // next image
                        imageCounter -= 1;
                        // loading logic
                        loadingBar.setVisibility(View.VISIBLE);
                        loadingText.setText("loading previous...");
                        // downloading the img and setting it to the imageResult
                        Picasso.get().load(imageDownload[imageCounter]).into(imageResult);
                        // stop loading
                        loadingBar.setVisibility(View.INVISIBLE);
                        loadingText.setText("");
                    }
                }
            }
        });

    }
}