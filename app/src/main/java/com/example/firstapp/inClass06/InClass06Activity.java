package com.example.firstapp.inClass06;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.firstapp.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class InClass06Activity extends AppCompatActivity {

    private Articles articles;
    private String category = "";
    private String country = "";
    private final String apiKey = "0f4302e216364fc198d275cec9bd4e0c";

    public final static String Profile_Key = "profile_key";
    private Spinner spinnerCountry;

    private Spinner spinnerCategory;
    
    private Button searchButton;

    private ListView listViewNews;

    private ArrayAdapter<News> adapterNews;

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
        setContentView(R.layout.activity_in_class06);

        // the UI elements
        spinnerCountry = findViewById(R.id.spinnerCountry);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        searchButton = findViewById(R.id.searchButton);
        listViewNews = findViewById(R.id.listViewNews);

        // spinner
        ArrayAdapter<CharSequence> adapterCountry=ArrayAdapter.createFromResource(this, R.array.countries, android.R.layout.simple_spinner_item);
        adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapterCategory=ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinnerCountry.setAdapter(adapterCountry);
        spinnerCategory.setAdapter(adapterCategory);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    category = spinnerCategory.getSelectedItem().toString();;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // do nothing
            }
        });

        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                country = spinnerCountry.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // do nothing
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // client
                OkHttpClient client = new OkHttpClient();
                String newsUrl = "";
                // request
                if (category.equals("") && country.equals("")) {
                    Toast.makeText(InClass06Activity.this, "Invalid category/country selection!", Toast.LENGTH_LONG).show();
                    return;
                } else if(category.equals("") && !country.equals("")) {
                    newsUrl = "https://newsapi.org/v2/top-headlines?country=" + country + "&apiKey=" + apiKey;
                } else if (!category.equals("") && country.equals("")) {
                    newsUrl = "https://newsapi.org/v2/top-headlines?category=" + category + "&apiKey=" + apiKey;
                } else if (!category.equals("") && !country.equals("")) {
                    newsUrl = "https://newsapi.org/v2/top-headlines?country=" + country + "&category=" + category + "&apiKey=" + apiKey;
                }
                Request newsRequest = new Request.Builder()
                        .url(newsUrl)
                        .build();

                // response
                if (isInternetAvailable()) {
                    client.newCall(newsRequest).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if (response.isSuccessful()) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Gson gsonData = new Gson();
                                        articles = gsonData.fromJson(response.body().charStream(), Articles.class);
                                        ArrayList<News> arrayArticles = articles.getArticles();
                                        adapterNews = new ArrayAdapter<News>(InClass06Activity.this,
                                                android.R.layout.simple_list_item_1,
                                                android.R.id.text1,
                                                arrayArticles);
                                        listViewNews.setAdapter(adapterNews);
                                    }
                                });
                            }

                        }
                    });
                }

            }
        });

        listViewNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // parcelable
                if (articles != null) {
                    News articleSelected = articles.getArticles().get(i);
                    News news = new News(articleSelected.source,
                            articleSelected.author,
                            articleSelected.title,
                            articleSelected.description,
                            articleSelected.url,
                            articleSelected.urlToImage,
                            articleSelected.publishedAt,
                            articleSelected.content);
                    // Go to the next activity
                    Intent toNewsActivity = new Intent(InClass06Activity.this, NewsActivity.class);


                    toNewsActivity.putExtra(Profile_Key, news);

                    startActivity(toNewsActivity);


                } else {
                    Toast.makeText(InClass06Activity.this, "Invalid news selection", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });




    }
}