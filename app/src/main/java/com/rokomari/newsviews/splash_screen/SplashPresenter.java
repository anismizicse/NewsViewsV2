package com.rokomari.newsviews.splash_screen;

public class SplashPresenter implements SplashContract.Presenter {

    private SplashContract.SplashView splashView;
    private SplashModel splashModel;

    SplashPresenter(SplashContract.SplashView splashView){
        this.splashView = splashView;
        splashModel = new SplashModel();
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

    }
}
