package com.georges.android.birthday_app_front.models;

import com.georges.android.birthday_app_front.utils.Util;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.util.Date;

public class Birthday {
    public String id;
    public Date date;
    public String firstname;
    public String lastname;

    public Birthday(String json) throws JSONException, ParseException {
        JSONObject jsonObject = new JSONObject(json);

        id = jsonObject.getString("id");
        date = Util.initDateFromDB(jsonObject.getString("date"));
        firstname = jsonObject.getString("firstname");
        lastname = jsonObject.getString("lastname");
    }

    public Birthday(String id, Date date, String firstname, String lastname) {
        this.id = id;
        this.date = date;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
            json.put("date", Util.printDate(date));
            json.put("firstname", firstname);
            json.put("lastname", lastname);
        } catch (JSONException e) {
        }
        return json;
    }

}
