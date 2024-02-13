package com.navod.etrade.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_USER_ID = "userId";
    public static void saveUserId(Context context, String userId){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(KEY_USER_ID, userId);
        edit.apply();
    }
    public static String getUserId(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ID, null);
    }
    public static void clearUserId(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.remove(KEY_USER_ID);
        edit.apply();
    }
}
