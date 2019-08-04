package com.rokomari.newsviews.home_screen;

import com.rokomari.newsviews.model.NewsDetails;

import java.util.List;

public interface HomeContract {

    interface HView{
        void onNewsLoaded(List<NewsDetails> newsList);
    }

    interface HPresenter{
        void loadNewsList();
        void onNewsLoaded(List<NewsDetails> newsList);
    }
}
