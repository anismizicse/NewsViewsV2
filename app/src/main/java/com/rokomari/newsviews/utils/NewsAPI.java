package com.rokomari.newsviews.utils;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsAPI {

    @GET(Constants.NEWS_URL_EXT)
    Call<NewsList> getNewsList();
}
