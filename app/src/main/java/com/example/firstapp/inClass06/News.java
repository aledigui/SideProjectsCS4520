package com.example.firstapp.inClass06;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class News implements Parcelable {

    Source source;
    String author;
    String title;
    String description;
    String url;
    String urlToImage;
    String publishedAt;
    String content;

    public News() {

    }

    public News(Source source, String author, String title, String description,
                String url, String urlToImage, String publishedAt, String content) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;


    }

    protected News(Parcel in) {
        //source = in.readParcelable(Source.class.getClassLoader());
        author = in.readString();
        title = in.readString();
        description = in.readString();
        url = in.readString();
        urlToImage = in.readString();
        publishedAt = in.readString();
        content = in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public Source getSource() {
        return this.source;
    }

    public void setSource(Source newSource) {
        this.source = newSource;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String newAuthor) {
        this.author = newAuthor;
    }

    public String getTitle() {
        return this.title;
    }

    public void settitle(String newtitle) {
        this.title = newtitle;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription (String newDescription) {
        this.description = newDescription;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String newUrl) {
        this.url = newUrl;
    }

    public String getUrlToImage() {
        return this.urlToImage;
    }

    public void setUrlToImage(String newImage) {
        this.urlToImage = newImage;
    }

    public String getPublishedAt() {
        return this.publishedAt;
    }

    public void setPublishedAt(String newPublished) {
        this.publishedAt = newPublished;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String newContent) {
        this.content = newContent;
    }

    public String toString() {
        return "News: " + title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        //parcel.writeParcelable(Source, i);
        parcel.writeString(author);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(url);
        parcel.writeString(urlToImage);
        parcel.writeString(publishedAt);
        parcel.writeString(content);
    }
}
