package com.rokomari.newsviews.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.rokomari.newsviews.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Methods {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfoMob = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo netInfoWifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return (netInfoMob != null && netInfoMob.isConnectedOrConnecting()) || (netInfoWifi != null && netInfoWifi.isConnectedOrConnecting());
    }

    public static boolean checkLogin(Context context){

        boolean isLoggedIn = false;

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        isLoggedIn = accessToken != null && !accessToken.isExpired();


        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        if(account != null){
            isLoggedIn = true;
        }

        return isLoggedIn;

    }

    public static void logout(Context context, GoogleSignInClient googleSignInClient){
        LoginManager.getInstance().logOut();
        googleSignInClient.signOut();
        googleSignInClient.revokeAccess();
        SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(context);
        sharedPrefUtil.clear();
    }

    public static String formatMessageTime(String dateString, Context context){

        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",
                Locale.getDefault());
        df.setTimeZone(TimeZone.getTimeZone("GMT"));  // IMP !!!
        try {

            date = df.parse(dateString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        String str = (String) DateUtils.getRelativeDateTimeString(

                context, // Suppose you are in an activity or other Context subclass

                date.getTime(), // The time to display

                DateUtils.MINUTE_IN_MILLIS, // The resolution. This will display only
                // minutes (no "3 seconds ago")


                DateUtils.WEEK_IN_MILLIS, // The maximum resolution at which the time will switch
                // to default date instead of spans. This will not
                // display "3 weeks ago" but a full date instead

                0); // Eventual flags

        return str;
    }

    public static void fadeInAnimation(final View view, final long animationDuration) {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(animationDuration);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.VISIBLE);
                fadeOutAnimation(view, animationDuration);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        view.startAnimation(fadeIn);
    }

    public static void fadeOutAnimation(final View view, final long animationDuration) {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(animationDuration);
        fadeOut.setDuration(animationDuration);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);
                fadeInAnimation(view,animationDuration);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        view.startAnimation(fadeOut);
    }
}
