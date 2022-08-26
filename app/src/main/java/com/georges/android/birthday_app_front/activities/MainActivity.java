package com.georges.android.birthday_app_front.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.LauncherActivity;
import android.os.Bundle;
import android.util.Log;
import com.georges.android.birthday_app_front.R;
import com.georges.android.birthday_app_front.adaptater.BirthdayAdaptater;
import com.georges.android.birthday_app_front.models.Birthday;
import com.georges.android.birthday_app_front.models.Users;
import com.georges.android.birthday_app_front.utils.Util;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String json;
    private Users mUserLogged;
    private RecyclerView mRecylerViewListBirthdays;
    private BirthdayAdaptater mAdapter;
    private List<Birthday> birthdayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        json = bundle.getString("userLogged");
        try {
            mUserLogged = new Users(json);
            Log.d("INTENT DU MAIN", json);
            Log.d("ID USER", mUserLogged.getId().toString());
            Log.d("ID NAME", mUserLogged.getUsername());
            birthdayList = mUserLogged.getBirthdays();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //binding de la recylcler view
        mRecylerViewListBirthdays = (RecyclerView) findViewById(R.id.my_recycler_view_list_birthdays);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecylerViewListBirthdays.setLayoutManager(layoutManager);

        //creation de l'instance adapter
        mAdapter = new BirthdayAdaptater(this, birthdayList);
        mRecylerViewListBirthdays.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();




        //ArrayList<LauncherActivity.ListItem> listItems = Util.createListItems(mUserLogged.birthdays);

    }












}