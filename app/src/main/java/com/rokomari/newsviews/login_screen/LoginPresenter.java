package com.rokomari.newsviews.login_screen;

import android.content.Context;

public class LoginPresenter implements LoginContract.LPresenter {

    LoginContract.LoginView loginView;

    LoginPresenter(Context context, LoginContract.LoginView loginView){
        this.loginView = loginView;

    }
}
