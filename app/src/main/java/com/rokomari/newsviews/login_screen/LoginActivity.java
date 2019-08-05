package com.rokomari.newsviews.login_screen;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.RequestManager;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.rokomari.newsviews.R;
import com.rokomari.newsviews.home_screen.HomeActivity;
import com.rokomari.newsviews.splash_screen.SplashActivity;
import com.rokomari.newsviews.utils.AppSingleTon;
import com.rokomari.newsviews.utils.Constants;
import com.rokomari.newsviews.utils.Methods;
import com.rokomari.newsviews.utils.SharedPrefUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView {

    private static final String TAG = "LoginActivity";
    private static final String EMAIL = "email";
        private static final String PUBLIC_PROFILE = "public_profile";
    private static final int RC_SIGN_IN = 11;
    ImageView app_logo;

    RequestManager requestManager;
    GoogleSignInClient mGoogleSignInClient;
    CallbackManager callbackManager;
    LoginButton loginButton;
    SignInButton gmailLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        app_logo = findViewById(R.id.app_logo);

        requestManager = AppSingleTon.getRequestManager(getApplicationContext());

        requestManager.load(R.drawable.rokomari).into(app_logo);

        gmailLogin = findViewById(R.id.gmailLogin);
        gmailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginWithGmail();
            }
        });

        setupGmailLogin();

        setupFBLogin();

        checkSplashStatus();

    }


    private void checkSplashStatus() {

        int userVisits = new SharedPrefUtil(this).getInt(Constants.USER_VISITS, 0);
        boolean isLogged = Methods.checkLogin(this);

        if(isLogged && userVisits != 2){
            startActivity(new Intent(this, SplashActivity.class));
            finish();
        }else if(isLogged){
            sendToHome();
        }
    }

    private void sendToHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    private void setupFBLogin() {

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.facebookLogin);
        loginButton.setReadPermissions(Arrays.asList(EMAIL, PUBLIC_PROFILE));

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                fetchFBProfile(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

                Log.d(TAG, "onCancel: ");
            }

            @Override
            public void onError(FacebookException exception) {

                Log.d(TAG, "onError: "+exception.getMessage());
            }
        });
    }

    private void setupGmailLogin() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void fetchFBProfile(AccessToken accessToken) {

        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("LoginActivity", response.toString());

                        try {
                            String name = object.getString("name");
                            String email = object.getString("email");
                            saveUserData(name, email);

                            sendToHome();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields","name, email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void saveUserData(String name, String email) {
        SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(this);
        sharedPrefUtil.saveString(Constants.USER_NAME, name);
        sharedPrefUtil.saveString(Constants.USER_EMAIL, email);
    }


    public void loginWithGmail() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            fetchGmailProfile(task);
        }
    }

    private void fetchGmailProfile(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            if (account != null) {
                saveUserData(account.getDisplayName(), account.getEmail());
                sendToHome();
            }

        } catch (ApiException e) {

            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());

        }
    }
}
