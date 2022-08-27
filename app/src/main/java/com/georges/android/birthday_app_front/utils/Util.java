package com.georges.android.birthday_app_front.utils;

import android.app.LauncherActivity;
import android.content.Context;
import android.util.Log;
import android.util.Patterns;

import com.georges.android.birthday_app_front.activities.MainActivity;
import com.georges.android.birthday_app_front.models.Birthday;
import com.georges.android.birthday_app_front.models.Users;
import org.json.JSONException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class Util {

    private static final String PREF_FILE = "pref_file";
    private static final String USER = "user";
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat FORMAT_INPUT = new SimpleDateFormat("dd/MM/yyyy");
    private static Calendar cal = null;
    private static Calendar cal1 = null;
    private static Calendar cal2 = null;
    private static final String[] MONTH_LIST = { "JANVIER", "FEVRIER", "MARS", "AVRIL", "MAI","JUIN","JUILLET","AOUT","SEPTEMEBRE","OCTOBRE","NOVEMBRE","DECEMBRE" };


    public static void setUser(Context context, String json) {
        // TODO : sauvegarder
    }

    public static Users getUser(Context context) throws JSONException, ParseException {
        // TODO : restaurer
        return null;
    }


    public static boolean isPasswordValid(String password) {
        if(password.length() > 3 && password.length() < 50 ){
            return true;
        }else {
            return false;
        }
    }

    public static long getAge(Date date) {
        long diff = System.currentTimeMillis() - date.getTime();
        return diff / 31622400000l;
    }

    public static String getDayFromDate(Date date){
        cal = Calendar.getInstance();
        cal.setTime(date);
        String month = cal.get(Calendar.MONTH)+"";
        return cal.get(Calendar.DAY_OF_MONTH)+"";
    }

    public static String getMonthfromDate(Date date){
        cal = Calendar.getInstance();
        cal.setTime(date);
        return MONTH_LIST[cal.get(Calendar.MONTH)];

    }

    public static boolean isUsernameValid(String password) {
        if(password.length() > 3 && password.length() < 30 ){
            return true;
        }else {
            return false;
        }
    }

    public static String printDate(Date date) {
        return FORMAT.format(date);
    }

    public static Date initDateFromDB(String str) throws ParseException {
        return FORMAT.parse(str);
    }

    public static List<Birthday> createSortedListItems(List<Birthday> birthdays) {
        List<LauncherActivity.ListItem> listItems = new ArrayList<>();

        Collections.sort(birthdays, new Comparator<Birthday>() {
            @Override
            public int compare(Birthday b1, Birthday b2) {
                cal1 = Calendar.getInstance();
                cal1.setTime(b1.date);
                cal2 = Calendar.getInstance();
                cal2.setTime(b2.date);
                if (cal1.get(Calendar.MONTH) < cal2.get(Calendar.MONTH))
                    return -1;
                else if (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH))
                    return 0;
                else
                    return 1;
            }
        });
        return birthdays;
    }
    
    

    public static Date initDateFromEditText(String str) throws ParseException {
        return FORMAT_INPUT.parse(str);
    }


}
