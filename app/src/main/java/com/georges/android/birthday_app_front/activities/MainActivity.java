package com.georges.android.birthday_app_front.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.LauncherActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.georges.android.birthday_app_front.R;
import com.georges.android.birthday_app_front.adaptater.BirthdayAdaptater;
import com.georges.android.birthday_app_front.models.Birthday;
import com.georges.android.birthday_app_front.models.Users;
import com.georges.android.birthday_app_front.utils.ApiCallBack;
import com.georges.android.birthday_app_front.utils.Util;
import com.georges.android.birthday_app_front.utils.UtilApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements ApiCallBack {

    private String json;
    private Users mUserLogged;
    private RecyclerView mRecylerViewListBirthdays;
    private BirthdayAdaptater mAdapter;
    private List<Birthday> mBirthdayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        json = bundle.getString("userLogged");

        Log.d("INTENT DU MAIN", json);

        try {
            mUserLogged = new Users(json);
            Log.d("try", json);
            mBirthdayList = mUserLogged.getBirthdays();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("catch json", json);
        } catch (ParseException e) {
            Log.d("catch parse", json);
            e.printStackTrace();
        }


        //binding recylcler view
        mRecylerViewListBirthdays = (RecyclerView) findViewById(R.id.my_recycler_view_list_birthdays);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecylerViewListBirthdays.setLayoutManager(layoutManager);

        //crea instance adapter
        mAdapter = new BirthdayAdaptater(this, mBirthdayList, mUserLogged.id);
        Log.d("id user in main", mUserLogged.id.toString());
        Log.d("id user in main", mUserLogged.getId().toString());
        mRecylerViewListBirthdays.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        //créa btn add birthday
        FloatingActionButton mBtnAddBirthday;
        mBtnAddBirthday = findViewById(R.id.btn_add_birthday);
        mBtnAddBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_new_birthday, null);
                EditText newFirstname = (EditText) v.findViewById(R.id.edit_text_add_birthday_firstname);
                EditText newLastname = (EditText) v.findViewById(R.id.edit_text_add_birthday_lastname);
                EditText newDate = (EditText) v.findViewById(R.id.edit_text_add_birthday_date);
                builder.setView(v);
                builder.setTitle("Nouvel Anniversaire ?" );
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String addNewBirthdayFirstname = newFirstname.getText().toString();
                        String addNewBirthdayLastname = newLastname.getText().toString();
                        String addNewBirthdayDate = newDate.getText().toString();
                        addNewBirthday(addNewBirthdayFirstname,addNewBirthdayLastname,addNewBirthdayDate);
                    }
                });
                builder.setNegativeButton("ANNULER", null);
                builder.create().show();
            }
        });


        //ArrayList<LauncherActivity.ListItem> listItems = Util.createListItems(mUserLogged.birthdays);

    }

    public Long getUserLoggedId(){
        return mUserLogged.getId();
    }


    public void addNewBirthday(String addNewBirthdayFirstname, String addNewBirthdayLastname, String addNewBirthdayDate) {
        Date date = null;
        try {
            date = Util.initDateFromEditText(addNewBirthdayDate);
            Birthday birthday = new Birthday(null,date,addNewBirthdayFirstname, addNewBirthdayLastname);

            Map<String, String> map = new HashMap<>();
            map.put("date", Util.printDate(birthday.date));
            map.put("firstname", birthday.firstname);
            map.put("lastname", birthday.lastname);
            String urlPost = UtilApi.URL_BIRTHDAY+"/"+mUserLogged.getId().toString()+"/birthdays";
            UtilApi.post(urlPost,map,MainActivity.this);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void fail(String json) {
        Log.d("FAIL ADD BIRTHDAY", "fail: " + json);
    }

    @Override
    public void success(String json) {
        Log.d("SUCCES ADD BIRTHDAY", "success: " + json);
        Snackbar.make(findViewById(R.id.coordinator_root), "Anniversaire ajouté", Snackbar.LENGTH_LONG).show();
    }
}