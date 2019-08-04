package com.rokomari.newsviews.utils;

import com.rokomari.newsviews.model.NewsDetails;
import com.rokomari.newsviews.model.NewsList;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsAPI {

    @GET(Constants.NEWS_URL_EXT)
    Call<NewsList> getNewsList();
}
