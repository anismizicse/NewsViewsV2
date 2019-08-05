package com.rokomari.newsviews.view.fragments;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rokomari.newsviews.R;
import com.rokomari.newsviews.utils.HomeContract;
import com.rokomari.newsviews.presenter.HomePresenter;
import com.rokomari.newsviews.view.adapters.NewsListAdapter;
import com.rokomari.newsviews.utils.NewsDetails;
import com.rokomari.newsviews.utils.Constants;
import com.rokomari.newsviews.utils.Methods;
import com.rokomari.newsviews.view.adapters.NewsListAdapter2;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeContract.HView {

    private HomeContract.HPresenter homePresenter;
    private RecyclerView rv_home;
    private NewsListAdapter newsListAdapter;
    private NewsListAdapter2 newsListAdapter2;
    private SwipeRefreshLayout pullToRefresh;
    private ProgressBar newsLoadProgress;
    private FloatingActionButton swap_news_list;
    private List<NewsDetails> newsList;
    private boolean listCard = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_home, container, false);

        pullToRefresh = view.findViewById(R.id.pullToRefresh);
        newsLoadProgress = view.findViewById(R.id.newsLoadProgress);
        swap_news_list = view.findViewById(R.id.swap_news_list);

        rv_home = view.findViewById(R.id.rv_home);
        rv_home.setItemAnimator(new DefaultItemAnimator());
        rv_home.setHasFixedSize(true);


        homePresenter = new HomePresenter(getActivity().getApplicationContext(), this);
        homePresenter.loadNewsList();

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                homePresenter.loadNewsList();
                pullToRefresh.setRefreshing(true);
            }
        });

        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(networkReceiver, intentFilter);

        swap_news_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(newsList != null && listCard){
                    listCard = false;
                    setListAdapter(listCard);
                }else if(newsList != null){
                    listCard = true;
                    setListAdapter(listCard);
                }

            }
        });

        return view;
    }

    @Override
    public void onNewsLoaded(List<NewsDetails> newsList) {
        newsLoadProgress.setVisibility(View.GONE);
        this.newsList = newsList;
        setListAdapter(listCard);
    }

    private void setListAdapter(boolean listCard) {

        newsListAdapter = null;
        rv_home.setLayoutManager(null);
        rv_home.setAdapter(null);

        if(listCard) {
            int spancount = 2;
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), spancount);
            rv_home.setLayoutManager(gridLayoutManager);
            newsListAdapter = new NewsListAdapter(getActivity(), newsList);
            rv_home.setAdapter(newsListAdapter);

        }else{
            rv_home.setLayoutManager(new LinearLayoutManager(getActivity()));
            newsListAdapter2 = new NewsListAdapter2(getActivity(), newsList);
            rv_home.setAdapter(newsListAdapter2);
        }
        pullToRefresh.setRefreshing(false);
    }

    private BroadcastReceiver networkReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (Constants.CON_ACTION.equals(intent.getAction())) {

                if (Methods.isNetworkAvailable(context)) {

                } else {

                    if (newsListAdapter == null) {
                        showInternetError(true);
                    } else {
                        showInternetError(false);
                    }
                }

            }

        }
    };

    private void showInternetError(final boolean withAction) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.no_internet);
        builder.setMessage(R.string.news_load_msg);
        if (withAction) {
            builder.setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                    homePresenter.loadNewsList();


                }
            });
        }
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(networkReceiver);
    }
}
