package com.rokomari.newsviews.home_screen;

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
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rokomari.newsviews.R;
import com.rokomari.newsviews.model.NewsDetails;
import com.rokomari.newsviews.utils.Constants;
import com.rokomari.newsviews.utils.Methods;

import java.util.List;

public class HomeFragment extends Fragment implements HomeContract.HView {

    private HomeContract.HPresenter homePresenter;
    private RecyclerView rv_home;
    private NewsListAdapter newsListAdapter;
    private SwipeRefreshLayout pullToRefresh;
    private ProgressBar newsLoadProgress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_home, container, false);

        pullToRefresh = view.findViewById(R.id.pullToRefresh);
        newsLoadProgress = view.findViewById(R.id.newsLoadProgress);

        rv_home = view.findViewById(R.id.rv_home);
        int spancount = 2;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), spancount);
        rv_home.setLayoutManager(gridLayoutManager);
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

        return view;
    }

    @Override
    public void onNewsLoaded(List<NewsDetails> newsList) {
        newsLoadProgress.setVisibility(View.GONE);
        newsListAdapter = new NewsListAdapter(getActivity(), newsList);
        rv_home.setAdapter(newsListAdapter);
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
