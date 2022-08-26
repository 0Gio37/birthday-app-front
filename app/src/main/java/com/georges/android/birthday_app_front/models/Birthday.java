package com.georges.android.birthday_app_front.models;

import com.georges.android.birthday_app_front.utils.Util;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.util.Date;

public class Birthday {
    public Date date;
    public String firstname;
    public String lastname;

    public Birthday(String json) throws JSONException, ParseException {
        JSONObject jsonObject = new JSONObject(json);

        date = Util.initDateFromDB(jsonObject.getString("birthday-date"));
        firstname = jsonObject.getString("birthday-firstname");
        lastname = jsonObject.getString("birthday-lastname");
    }

    public Birthday(Date date, String firstname, String lastname) {
        this.date = date;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("birthday-date", Util.printDate(date));
            json.put("birthday-firstname", firstname);
            json.put("birthday-lastname", lastname);
        } catch (JSONException e) {
        }
        return json;
    }

}
