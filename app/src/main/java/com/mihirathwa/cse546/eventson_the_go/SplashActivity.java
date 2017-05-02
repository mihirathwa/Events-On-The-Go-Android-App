package com.mihirathwa.cse546.eventson_the_go;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    LoginButton loginButton;
    TextView loginStatus;
    CallbackManager callbackManager;

    private static int SPLASH_SCREEN_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        loginButton = (LoginButton) findViewById(R.id.AS_facebookLoginBtn);
        loginButton.setReadPermissions(getFacebookPermissions());

        loginStatus = (TextView) findViewById(R.id.AS_loginStatus);

        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(SplashActivity.this, "Login Success", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SplashActivity.this, EventsMapActivity.class);
                startActivity(intent);

                finish();
            }

            @Override
            public void onCancel() {
                Toast.makeText(SplashActivity.this, "Login Cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(SplashActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
            }
        });

//        //set delay at Splash Screen
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(SplashActivity.this, EventsMapActivity.class);
//                startActivity(intent);
//
//                finish();
//            }
//        }, SPLASH_SCREEN_DELAY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    protected List<String> getFacebookPermissions() {
        List<String> userPermissions = new ArrayList<>();

        userPermissions.add("email");
        userPermissions.add("user_likes");
        userPermissions.add("user_location");
        userPermissions.add("user_events");

        return userPermissions;
    }
}
