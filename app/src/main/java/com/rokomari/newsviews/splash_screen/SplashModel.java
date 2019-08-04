package com.rokomari.newsviews.splash_screen;


import android.content.Context;

import com.rokomari.newsviews.utils.Constants;
import com.rokomari.newsviews.utils.SharedPrefUtil;

public class SplashModel {

    Context context;
    SplashContract.Presenter presenter;

    SplashModel(Context context, SplashContract.Presenter presenter){
        this.context = context;
        this.presenter = presenter;
    }

    void checkUserVist(){
        int visits = new SharedPrefUtil(context).getInt(Constants.USER_VISITS, 0);
        presenter.onVisitsLoaded(visits);
    }

}
