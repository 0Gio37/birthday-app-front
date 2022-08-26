package com.georges.android.birthday_app_front.utils;

import android.app.LauncherActivity;
import android.content.Context;
import android.util.Patterns;

import com.georges.android.birthday_app_front.models.Birthday;
import com.georges.android.birthday_app_front.models.Users;
import org.json.JSONException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class Util {

    private static final String PREF_FILE = "pref_file";
    private static final String USER = "user";
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat FORMAT_INPUT = new SimpleDateFormat("dd/MM/yyyy");
    private static Calendar cal = null;
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

    public static ArrayList<LauncherActivity.ListItem> createListItems(ArrayList<Birthday> birthdays) {

        ArrayList<LauncherActivity.ListItem> listItems = new ArrayList<>();

        int monthNumber = 0;
        String[] months = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Décembre"};

        // TODO : trier la liste en fonction des mois d'anniversaire

        return listItems;
    }

    public static Date initDateFromEditText(String str) throws ParseException {
        return FORMAT_INPUT.parse(str);
    }


}
