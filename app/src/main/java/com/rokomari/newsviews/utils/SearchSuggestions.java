package com.rokomari.newsviews.utils;

import android.content.SearchRecentSuggestionsProvider;

public class SearchSuggestions extends SearchRecentSuggestionsProvider {

    public final static String AUTHORITY = "com.rokomari.newsviews.utils.SearchSuggestions";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SearchSuggestions() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
