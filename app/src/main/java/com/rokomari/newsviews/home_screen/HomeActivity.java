package com.rokomari.newsviews.home_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rokomari.newsviews.R;

public class HomeActivity extends AppCompatActivity implements HomeContract.HView {

    private HomePresenter homePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homePresenter = new HomePresenter(this);
    }
}
