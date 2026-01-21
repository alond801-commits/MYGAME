package com.idodrori.mygame.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
    private static final String PREF_NAME = "MyGamePrefs";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String USER_ID = "userId"; // Added key

    public static boolean isUserLoggedIn(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(IS_LOGGED_IN, false);
    }

    public static String getUserId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(USER_ID, null);
    }

    public static void setUserLoggedIn(Context context, boolean isLoggedIn, String id) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn);
        editor.putString(USER_ID, id); // Save the ID
        editor.apply();
    }
}