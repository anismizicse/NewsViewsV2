package com.rokomari.newsviews.model;


import android.content.Context;

import com.rokomari.newsviews.utils.SplashContract;
import com.rokomari.newsviews.utils.Constants;
import com.rokomari.newsviews.utils.SharedPrefUtil;

public class SplashModel {

    Context context;
    SplashContract.Presenter presenter;

    public SplashModel(Context context, SplashContract.Presenter presenter){
        this.context = context;
        this.presenter = presenter;
    }

    public void checkUserVist(){
        int visits = new SharedPrefUtil(context).getInt(Constants.USER_VISITS, 0);
        presenter.onVisitsLoaded(visits);
    }

}
