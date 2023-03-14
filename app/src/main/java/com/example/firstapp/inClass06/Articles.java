package com.example.firstapp.inClass06;

import java.util.ArrayList;

public class Articles {

    private ArrayList<News> articles;

    public Articles() {}

    public ArrayList<News> getArticles() {
        return this.articles;
    }

    public void setArticles (ArrayList<News> newArticles) {
        this.articles = newArticles;
    }

    public String toString() {
        return "Articles{" +
                "articles=" + articles +
                "}";
    }

}
