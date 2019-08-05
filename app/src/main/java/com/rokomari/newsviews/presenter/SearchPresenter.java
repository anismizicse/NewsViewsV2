package com.rokomari.newsviews.presenter;

import android.content.Context;

import com.rokomari.newsviews.model.SearchModel;
import com.rokomari.newsviews.utils.SearchContract;

public class SearchPresenter implements SearchContract.SPresenter {

    SearchContract.SearchView searchView;
    SearchModel searchModel;

    public SearchPresenter(Context context, SearchContract.SearchView searchView){
        this.searchView = searchView;
        searchModel = new SearchModel(context, this);
    }

    @Override
    public void loadNumAPI(String number) {
        searchModel.fetchNumberDetails(number);
    }

    @Override
    public void onNumAPILoaded(String details) {
        searchView.onNumAPILoaded(details);
    }

}
