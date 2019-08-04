package com.rokomari.newsviews.home_screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rokomari.newsviews.R;
import com.rokomari.newsviews.model.NewsDetails;

import java.util.List;

public class HomeFragment extends Fragment implements HomeContract.HView {

    private HomeContract.HPresenter homePresenter;
    private RecyclerView rv_home;
    private NewsListAdapter newsListAdapter;
    private SwipeRefreshLayout pullToRefresh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_home, container, false);

        pullToRefresh = view.findViewById(R.id.pullToRefresh);

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

        return view;
    }

    @Override
    public void onNewsLoaded(List<NewsDetails> newsList) {
        newsListAdapter = new NewsListAdapter(getActivity(), newsList);
        rv_home.setAdapter(newsListAdapter);
        pullToRefresh.setRefreshing(false);
    }
}
