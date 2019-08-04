package com.rokomari.newsviews.home_screen;

import android.content.Context;

import com.rokomari.newsviews.model.NewsDetails;

import java.util.List;

public class HomePresenter implements HomeContract.HPresenter {

    private HomeContract.HView hView;
    private HomeModel homeModel;

    HomePresenter(Context context, HomeContract.HView hView){
        this.hView = hView;
        homeModel = new HomeModel(context, this);
    }

    @Override
    public void loadNewsList() {
        homeModel.loadArticles();
    }

    @Override
    public void onNewsLoaded(List<NewsDetails> newsDetailsList) {
        hView.onNewsLoaded(newsDetailsList);
    }
}
