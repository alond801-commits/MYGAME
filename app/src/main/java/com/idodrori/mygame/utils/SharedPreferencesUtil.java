package com.idodrori.mygame.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {

    private static final String PREF_NAME = "MyGamePrefs";
    private static final String IS_LOGGED_IN = "isLoggedIn";

    // פונקציה לבדיקה האם המשתמש מחובר
    public static boolean isUserLoggedIn(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(IS_LOGGED_IN, false);
    }

    // פונקציה לעדכון מצב החיבור (לקרוא לה אחרי התחברות מוצלחת)
    public static void setUserLoggedIn(Context context, boolean isLoggedIn) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }
}