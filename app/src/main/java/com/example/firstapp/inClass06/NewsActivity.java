package com.example.firstapp.inClass06;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firstapp.R;
import com.example.firstapp.inClass06.InClass06Activity;
import com.example.firstapp.inClass06.News;
import com.squareup.picasso.Picasso;

public class NewsActivity extends AppCompatActivity {
    private TextView authorSelected;
    private TextView titleSelected;
    private TextView descriptionSelected;
    private TextView urlSelected;
    private TextView publishedSelected;
    private TextView contentSelected;

    private ImageView imageUrlSelected;
    
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        authorSelected = findViewById(R.id.authorSelected);
        titleSelected = findViewById(R.id.titleSelected);
        descriptionSelected = findViewById(R.id.descriptionSelected);
        urlSelected = findViewById(R.id.urlSelected);
        publishedSelected = findViewById(R.id.publishedSelected);
        contentSelected = findViewById(R.id.contentSelected);
        imageUrlSelected = findViewById(R.id.imageUrlSelected);


        if(getIntent() != null && getIntent().getExtras() != null) {
            News news = getIntent().getParcelableExtra(InClass06Activity.Profile_Key);

            authorSelected.setText(news.getAuthor());
            titleSelected.setText(news.getTitle());
            descriptionSelected.setText(news.getDescription());
            descriptionSelected.setMovementMethod(new ScrollingMovementMethod());
            urlSelected.setText(Html.fromHtml("<a href=" + news.getUrl() +">Go to the news!</a> "));
            urlSelected.setClickable(true);
            urlSelected.setMovementMethod(LinkMovementMethod.getInstance());
            imageUrl = news.getUrlToImage();
            publishedSelected.setText(news.getPublishedAt());
            contentSelected.setText(news.getContent());
            if (imageUrl != null) {
                Picasso.get().load(imageUrl).into(imageUrlSelected);
            }


        }


    }
}