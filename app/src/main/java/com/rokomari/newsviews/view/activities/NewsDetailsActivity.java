package com.rokomari.newsviews.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.rokomari.newsviews.R;
import com.rokomari.newsviews.utils.Constants;

public class NewsDetailsActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar progressBar;
    String newsLink = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        if(getIntent().hasExtra(Constants.NEWS_LINK)){
            newsLink = getIntent().getStringExtra(Constants.NEWS_LINK);
        }

        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);

        webView.requestFocus();
        webView.getSettings().setLightTouchEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.setSoundEffectsEnabled(true);
        webView.loadUrl(newsLink);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    progressBar.setVisibility(View.VISIBLE);
                }
                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
