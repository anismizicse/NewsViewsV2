package com.rokomari.newsviews.utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.rokomari.newsviews.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppSingleTon {

    private static Retrofit retrofit;
    private static RequestManager requestManager;

    public static synchronized Retrofit getmInstance(Context context, String base_url){

        if(retrofit == null){
            return new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(Constants.getUnsafeOkHttpClient())
                    .build();
        }

        return retrofit;
    }

    public static synchronized RequestManager getRequestManager(Context context){

        if(requestManager == null){
            return Glide.with(context)
                    .setDefaultRequestOptions(RequestOptions.placeholderOf(R.drawable.rokomari)
                            .error(R.drawable.rokomari));
        }

        return requestManager;
    }
}
