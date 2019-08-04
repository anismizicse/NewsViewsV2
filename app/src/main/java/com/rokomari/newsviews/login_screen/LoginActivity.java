package com.rokomari.newsviews.login_screen;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.RequestManager;
import com.rokomari.newsviews.R;
import com.rokomari.newsviews.home_screen.HomeActivity;
import com.rokomari.newsviews.utils.AppSingleTon;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    ImageView app_logo;

    RequestManager requestManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        app_logo = findViewById(R.id.app_logo);

        requestManager = AppSingleTon.getRequestManager(getApplicationContext());

        requestManager.load(R.drawable.rokomari).into(app_logo);

    }

    public void loginWithFB(View view) {
        startActivity(new Intent(this, HomeActivity.class));
    }

    public void loginWithGmail(View view) {
    }
}
