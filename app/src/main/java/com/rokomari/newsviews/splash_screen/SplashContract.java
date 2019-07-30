package com.rokomari.newsviews.splash_screen;

public interface SplashContract {

    interface SplashView{
        void loadNextActivity();
        void changeSlide();
        void onSlideSelected(int position);
        void shouldShowSplash(boolean splash);
    }

    interface Presenter{
        void onSkipClicked();
        void onNextClicked();
        void onDoneClicked();
        void onSlideChanged(int position);
        void checkUserVisit();
    }

}
