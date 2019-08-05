package com.rokomari.newsviews.login_screen;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.rokomari.newsviews.R;

public class LoginModel implements GoogleApiClient.OnConnectionFailedListener {

    Context context;
    LoginContract.LPresenter lPresenter;

    LoginModel(Context context, LoginContract.LPresenter lPresenter){
        this.context = context;
        this.lPresenter = lPresenter;
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
