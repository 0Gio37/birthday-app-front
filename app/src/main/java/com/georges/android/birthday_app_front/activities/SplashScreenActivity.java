package com.georges.android.birthday_app_front.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String json;
    String userStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = this.getSharedPreferences("logUserInfo", Context.MODE_PRIVATE);
        userStatus = sharedPreferences.getString("userLogged", "");
        json =  sharedPreferences.getString("jsonUser", "");

        if(userStatus == "logged"){
            sharedPreferences.getString("jsonUser", json);
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("userLogged",json);
            startActivity(intent);
        }else {
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();
    }
}