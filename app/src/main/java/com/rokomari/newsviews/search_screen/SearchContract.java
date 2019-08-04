package com.rokomari.newsviews.search_screen;

public interface SearchContract {

    interface SearchView{
        void onNumbersLoaded(String details);
        void onDatesLoaded(String details);
    }

    interface SPresenter{
        void loadNumbers(String number);
        void loadDates();
        void onNumberLoaded(String details);
        void onDatesLoaded(String details);
    }
}
