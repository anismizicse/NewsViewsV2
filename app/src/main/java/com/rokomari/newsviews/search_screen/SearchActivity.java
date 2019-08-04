package com.rokomari.newsviews.search_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.rokomari.newsviews.R;

public class SearchActivity extends AppCompatActivity implements SearchContract.SearchView {

    TextView searchResult;
    SearchContract.SPresenter sPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchResult = findViewById(R.id.searchResult);

        sPresenter = new SearchPresenter(getApplicationContext(), this);

        handleIntent(getIntent());
    }


    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            sPresenter.loadNumbers(query);
        }
    }

    @Override
    public void onNumbersLoaded(String details) {
        //Toast.makeText(this, details, Toast.LENGTH_LONG).show();
        searchResult.setText(details);
    }

    @Override
    public void onDatesLoaded(String details) {
        Toast.makeText(this, details, Toast.LENGTH_LONG).show();
    }
}
