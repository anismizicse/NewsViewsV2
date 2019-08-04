package com.rokomari.newsviews.splash_screen;

import android.content.Context;


public class SplashPresenter implements SplashContract.Presenter {

    private SplashContract.SplashView splashView;
    private SplashModel splashModel;

    public SplashPresenter(Context context, SplashContract.SplashView splashView){
        this.splashView = splashView;
        splashModel = new SplashModel(context, this);
    }

    @Override
    public void onSkipClicked() {
        splashView.loadNextActivity();
    }

    @Override
    public void onNextClicked() {
        splashView.changeSlide();
    }

    @Override
    public void onDoneClicked() {
        splashView.loadNextActivity();
    }

    @Override
    public void onSlideChanged(int position) {
        splashView.onSlideSelected(position);
    }

    @Override
    public void checkUserVisit() {
        splashModel.checkUserVist();
    }

    @Override
    public void onVisitsLoaded(int visits) {
        splashView.shouldShowSplash(visits);
    }
}
