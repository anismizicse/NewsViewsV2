package com.rokomari.newsviews.search_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rokomari.newsviews.R;
import com.rokomari.newsviews.utils.SearchSuggestions;

public class SearchActivity extends AppCompatActivity implements SearchContract.SearchView {

    TextView searchResult;
    SearchContract.SPresenter sPresenter;
    Toolbar toolbar;
    ProgressBar searchProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Search Results");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchResult = findViewById(R.id.searchResult);
        searchProgress = findViewById(R.id.searchProgress);

        sPresenter = new SearchPresenter(getApplicationContext(), this);

        Intent intent = getIntent();

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SearchSuggestions.AUTHORITY, SearchSuggestions.MODE);
            suggestions.saveRecentQuery(query, null);
        }

        handleIntent(intent);
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
        searchProgress.setVisibility(View.GONE);
        searchResult.setText(details);
    }

    @Override
    public void onDatesLoaded(String details) {
        Toast.makeText(this, details, Toast.LENGTH_LONG).show();
    }
}
