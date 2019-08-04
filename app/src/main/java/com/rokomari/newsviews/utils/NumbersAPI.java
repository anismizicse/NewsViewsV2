package com.rokomari.newsviews.utils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NumbersAPI {

    @GET("{number}")
    Call<ResponseBody> getNumOrDates(@Path("number") String number);
}
