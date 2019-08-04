package com.rokomari.newsviews.search_screen;

import android.content.Context;
import android.util.Log;

import com.rokomari.newsviews.utils.AppSingleTon;
import com.rokomari.newsviews.utils.Constants;
import com.rokomari.newsviews.utils.NumbersAPI;

import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchModel {
    private static final String TAG = "SearchModel";
    Context context;
    SearchContract.SPresenter sPresenter;

    SearchModel(Context context, SearchContract.SPresenter sPresenter){
        this.context = context;
        this.sPresenter = sPresenter;
    }

    void fetchNumberDetails(String number){
        NumbersAPI numbersAPI = AppSingleTon
                .getmInstance(context, Constants.NUMBER_BASE_URL)
                .create(NumbersAPI.class);
        
        Call<ResponseBody> call = numbersAPI.getNumOrDates(number);
        
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(!response.isSuccessful()){
                    Log.d(TAG, "onResponse: "+ response.code());
                    return;
                }

                Log.d(TAG, "onResponse: "+ response);
                try {
                    sPresenter.onNumberLoaded(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
        
        
    }
}
