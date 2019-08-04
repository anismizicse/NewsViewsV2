package com.rokomari.newsviews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NewsList {

    @SerializedName("articles")
    @Expose
    private ArrayList<NewsDetails> articles = null;

    public ArrayList<NewsDetails> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<NewsDetails> articles) {
        this.articles = articles;
    }


}
