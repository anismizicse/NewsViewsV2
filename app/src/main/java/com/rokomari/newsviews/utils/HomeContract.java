package com.rokomari.newsviews.utils;

import java.util.List;

public interface HomeContract {

    interface HView{
        void onNewsLoaded(List<NewsDetails> newsList);
    }

    interface HPresenter{
        void loadNewsList();
        void onNewsLoaded(List<NewsDetails> newsList);
    }

    interface SwitchList{

    }
}
