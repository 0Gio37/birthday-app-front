package com.georges.android.birthday_app_front.models;

import android.content.Context;
import android.util.Log;
import com.georges.android.birthday_app_front.utils.Util;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Users {

    public Long id;
    public String username;
    public String email;
    public ArrayList<Birthday> birthdays;

    public String stringJson;

    public Users(String json) throws JSONException, ParseException {

        stringJson = json;

        JSONObject jsonObject = new JSONObject(json);
        id = jsonObject.getLong("id");
        username = jsonObject.getString("username");
        email = jsonObject.getString("email");
        birthdays = new ArrayList<>();

        JSONArray jsonArray = jsonObject.getJSONArray("birthdays");
        for (int i = 0; i < jsonArray.length(); i++) {
            birthdays.add(new Birthday(jsonObject.getJSONArray("birthdays").getJSONObject(i).toString()));
        }
    }

    public List<Birthday> getBirthdays(){
        return birthdays;
    }

    public Long getId(){
        return this.id;
    }


    public void addBirthday(Context context, Birthday birthday) {
        birthdays.add(birthday);

        try {
            JSONObject jsonObject = new JSONObject(stringJson);
            jsonObject.getJSONArray("birthdays").put(birthday.toJson());
            stringJson = jsonObject.toString();

            Log.d("lol", "addBirthday: " + stringJson);

            Util.setUser(context, stringJson);
        } catch (JSONException e) {

        }
    }

}

