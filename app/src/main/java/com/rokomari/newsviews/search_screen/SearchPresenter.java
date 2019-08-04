package com.rokomari.newsviews.search_screen;

import android.content.Context;

public class SearchPresenter implements SearchContract.SPresenter {

    SearchContract.SearchView searchView;
    SearchModel searchModel;
    SearchPresenter(Context context, SearchContract.SearchView searchView){
        this.searchView = searchView;
        searchModel = new SearchModel(context, this);
    }

    @Override
    public void loadNumbers(String number) {
        searchModel.fetchNumberDetails(number);
    }

    @Override
    public void loadDates() {

    }

    @Override
    public void onNumberLoaded(String details) {
        searchView.onNumbersLoaded(details);
    }

    @Override
    public void onDatesLoaded(String details) {

    }
}
