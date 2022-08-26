package com.georges.android.birthday_app_front.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LauncherActivity;
import android.os.Bundle;
import android.util.Log;

import com.georges.android.birthday_app_front.R;
import com.georges.android.birthday_app_front.models.Users;
import com.georges.android.birthday_app_front.utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String json;
    private Users mUserLogged;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        json = bundle.getString("userLogged");
        try {
            mUserLogged = new Users(json);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ArrayList<LauncherActivity.ListItem> listItems = Util.createListItems(mUserLogged.birthdays);


        Log.d("id", mUserLogged.getId().toString());

    }












}