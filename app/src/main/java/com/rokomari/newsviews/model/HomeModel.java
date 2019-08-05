package com.rokomari.newsviews.model;

import android.content.Context;
import android.util.Log;

import com.rokomari.newsviews.utils.HomeContract;
import com.rokomari.newsviews.utils.NewsDetails;
import com.rokomari.newsviews.utils.NewsList;
import com.rokomari.newsviews.utils.AppSingleTon;
import com.rokomari.newsviews.utils.Constants;
import com.rokomari.newsviews.utils.NewsAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeModel {
    private static final String TAG = "HomeModel";
    Context context;
    HomeContract.HPresenter hPresenter;

    public HomeModel(Context context, HomeContract.HPresenter hPresenter){
        this.context = context;
        this.hPresenter = hPresenter;
    }

    public void loadArticles(){

        NewsAPI newsAPI = AppSingleTon.getmInstance(context, Constants.NEWS_BASE_URL)
                                .create(NewsAPI.class);

        Call<NewsList> call = newsAPI.getNewsList();

        call.enqueue(new Callback<NewsList>() {
            @Override
            public void onResponse(Call<NewsList> call, Response<NewsList> response) {

                if(!response.isSuccessful()){
                    Log.d(TAG, "onResponse: "+ response.code());
                    return;
                }

                Log.d(TAG, "onResponse: "+ response);

                List<NewsDetails> newsList = response.body().getArticles();
                hPresenter.onNewsLoaded(newsList);
                for (NewsDetails news: newsList) {
                    Log.d(TAG, "onResponse: "+ news.getTitle());
                }
            }

            @Override
            public void onFailure(Call<NewsList> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}
