package com.rokomari.newsviews.model;

import android.content.Context;
import com.rokomari.newsviews.utils.AppSingleTon;
import com.rokomari.newsviews.utils.Constants;
import com.rokomari.newsviews.utils.NumbersAPI;
import com.rokomari.newsviews.utils.SearchContract;

import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchModel {
    Context context;
    SearchContract.SPresenter sPresenter;

    public SearchModel(Context context, SearchContract.SPresenter sPresenter){
        this.context = context;
        this.sPresenter = sPresenter;
    }

    public void fetchNumberDetails(String number){
        NumbersAPI numbersAPI = AppSingleTon
                .getmInstance(context, Constants.NUMBER_BASE_URL)
                .create(NumbersAPI.class);
        
        Call<ResponseBody> call = numbersAPI.getNumOrDates(number);
        
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(!response.isSuccessful()){

                    return;
                }

                try {
                    sPresenter.onNumAPILoaded(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        
        
    }
}
