package com.rokomari.newsviews;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.facebook.login.Login;
import com.rokomari.newsviews.home_screen.HomeActivity;
import com.rokomari.newsviews.login_screen.LoginActivity;
import com.rokomari.newsviews.splash_screen.SplashActivity;
import com.rokomari.newsviews.utils.Constants;
import com.rokomari.newsviews.utils.Methods;
import com.rokomari.newsviews.utils.SharedPrefUtil;

public class WelcomeActivity extends AppCompatActivity {

    ImageView welcome_logo;
    private static final String TAG = "WelcomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcome_logo = findViewById(R.id.welcome_logo);

        Methods.fadeInAnimation(welcome_logo,3000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(!Methods.isNetworkAvailable(WelcomeActivity.this)){
                    showInternetError();
                }else{
                    checkSetup();
                }
            }
        },3000);


    }

    private void showInternetError(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.no_internet);
        builder.setMessage(R.string.please_connect);
        builder.setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(!Methods.isNetworkAvailable(WelcomeActivity.this)){
                    showInternetError();
                }else{
                    checkSetup();
                }
            }
        });
        builder.show();
    }

    private void checkSetup() {

        welcome_logo.clearAnimation();
        int userVisits = new SharedPrefUtil(this).getInt(Constants.USER_VISITS, 0);
        boolean isLogged = Methods.checkLogin(this);

        Log.d(TAG, "checkSetup: "+userVisits+ " "+isLogged);

        if(isLogged && userVisits != 2){
            startActivity(new Intent(this, SplashActivity.class));
        }else if(!isLogged && userVisits == 0){
            startActivity(new Intent(this, LoginActivity.class));
        }else{
            startActivity(new Intent(this, HomeActivity.class));
        }

        finish();
    }
}
