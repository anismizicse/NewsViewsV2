package com.rokomari.newsviews.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.rokomari.newsviews.R;
import com.rokomari.newsviews.utils.SplashContract;
import com.rokomari.newsviews.presenter.SplashPresenter;
import com.rokomari.newsviews.utils.Constants;
import com.rokomari.newsviews.utils.SharedPrefUtil;
import com.rokomari.newsviews.view.adapters.IntroFragAdapter;
import com.rokomari.newsviews.view.fragments.IntroSlideFragment;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity implements SplashContract.SplashView, View.OnClickListener {

    private SplashPresenter splashPresenter;
    TextView skipButton, doneButton, indicatorContainer;
    ImageView nextButton;
    private IntroFragAdapter mPagerAdapter;
    private ViewPager pager;
    private List<Fragment> fragments = new ArrayList<>();
    private int slidesNumber;
    private boolean showSkip = true;
    private boolean showDone = true;
    private int splashType = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);

        splashPresenter = new SplashPresenter(getApplicationContext(),this);

        splashPresenter.checkUserVisit();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        skipButton = findViewById(R.id.skip);
        nextButton = findViewById(R.id.next);
        doneButton = findViewById(R.id.done);
        indicatorContainer = findViewById(R.id.indicator_container);

        pager =  findViewById(R.id.view_pager);

        setViews();

        addSlides();


    }

    private void setViews() {

        skipButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        doneButton.setOnClickListener(this);

        mPagerAdapter = new IntroFragAdapter(getSupportFragmentManager(), fragments);

        pager.setAdapter(this.mPagerAdapter);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                splashPresenter.onSlideChanged(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setIndicator(int currentSlide) {
        String indicatorNum = currentSlide + "/" + slidesNumber;
        indicatorContainer.setText(indicatorNum);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.skip:
                splashPresenter.onSkipClicked();
                break;
            case R.id.next:
                splashPresenter.onNextClicked();
                break;
            case R.id.done:
                splashPresenter.onDoneClicked();
                break;
        }
    }


    public void addSlides() {

        if(splashType == 0) {
            fragments.add(IntroSlideFragment.newInstance(R.layout.firstintro1));
            fragments.add(IntroSlideFragment.newInstance(R.layout.firstintro2));
            fragments.add(IntroSlideFragment.newInstance(R.layout.firstintro3));
        }else if(splashType == 1){
            fragments.add(IntroSlideFragment.newInstance(R.layout.secondintro1));
            fragments.add(IntroSlideFragment.newInstance(R.layout.secondintro2));
            fragments.add(IntroSlideFragment.newInstance(R.layout.secondintro3));
        }


        mPagerAdapter.notifyDataSetChanged();

        slidesNumber = fragments.size();
        setIndicator(1);

        if (slidesNumber == 1) {
            nextButton.setVisibility(View.GONE);
            doneButton.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void loadNextActivity() {

        if(splashType == 0){
            new SharedPrefUtil(this).saveInt(Constants.USER_VISITS, 1);
        }else if(splashType == 1){
            new SharedPrefUtil(this).saveInt(Constants.USER_VISITS, 2);
        }
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void changeSlide() {
        pager.setCurrentItem(pager.getCurrentItem() + 1);
    }

    @Override
    public void onSlideSelected(int position) {
        if (slidesNumber > 1) {
            setIndicator(position + 1);
        }
        if (position == slidesNumber - 1) {
            skipButton.setVisibility(View.INVISIBLE);
            nextButton.setVisibility(View.GONE);
            if (showDone) {
                doneButton.setVisibility(View.VISIBLE);
            } else {
                doneButton.setVisibility(View.INVISIBLE);
            }
        } else {
            skipButton.setVisibility(View.VISIBLE);
            doneButton.setVisibility(View.GONE);
            nextButton.setVisibility(View.VISIBLE);
        }

        if (!showSkip) {
            skipButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void shouldShowSplash(int visits) {

        if(visits == 0){
            splashType = 0;
        }else if(visits == 1){
            splashType = 1;
        }else{
            loadNextActivity();
        }
    }
}
