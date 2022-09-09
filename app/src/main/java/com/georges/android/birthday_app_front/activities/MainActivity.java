package com.georges.android.birthday_app_front.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        json = bundle.getString("userLogged");
        Log.d("INTENT DU MAIN", json);

        try {
            mUserLogged = new Users(json);
            saveLoggedUser();
            mBirthdayList = mUserLogged.getBirthdays();
            Util.createSortedListItems(mBirthdayList);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //binding recylcler view
        mRecylerViewListBirthdays = (RecyclerView) findViewById(R.id.my_recycler_view_list_birthdays);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecylerViewListBirthdays.setLayoutManager(layoutManager);

        //crea instance adapter
        mAdapter = new BirthdayAdaptater(this, mBirthdayList, mUserLogged.id);
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

            //MAJ birthdayList recycler and list of birhdays
            mBirthdayList.add(birthday);
            mAdapter.notifyDataSetChanged();

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
        Snackbar.make(findViewById(R.id.coordinator_root), "Anniversaire ajouté", Snackbar.LENGTH_LONG).show();
    }

    private void saveLoggedUser(){
        sharedPreferences = this.getSharedPreferences("logUserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userLogged", "logged");
        editor.putString("jsonUser", json);
        editor.apply();
    }


}