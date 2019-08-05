package com.rokomari.newsviews.utils;

public interface SearchContract {

    interface SearchView{
        void onNumAPILoaded(String details);
    }

    interface SPresenter{
        void loadNumAPI(String number);
        void onNumAPILoaded(String details);
    }
}
