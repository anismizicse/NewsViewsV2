package com.rokomari.newsviews.presenter;

import android.content.Context;

import com.rokomari.newsviews.utils.HomeContract;
import com.rokomari.newsviews.model.HomeModel;
import com.rokomari.newsviews.utils.NewsDetails;

import java.util.List;

public class HomePresenter implements HomeContract.HPresenter {

    private HomeContract.HView hView;
    private HomeModel homeModel;

    public HomePresenter(Context context, HomeContract.HView hView){
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
