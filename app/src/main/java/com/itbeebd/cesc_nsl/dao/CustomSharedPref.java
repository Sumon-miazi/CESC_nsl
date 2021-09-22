package com.itbeebd.cesc_nsl.dao;

import android.content.Context;
import android.content.SharedPreferences;

import com.itbeebd.cesc_nsl.R;

public class CustomSharedPref {
    private final SharedPreferences sharedPreferences;
    private static CustomSharedPref customSharedPref;

    private CustomSharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.sharedPrefName), Context.MODE_PRIVATE);
    }  //private constructor.

    public static CustomSharedPref getInstance(Context context) {
        if (customSharedPref == null) {
            customSharedPref = new CustomSharedPref(context);
        }
        return customSharedPref;
    }

    public String getUserId() {
        return sharedPreferences.getString("UserId", "");
    }

    public void setUserId(String flag) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserId", flag);
        editor.apply();
    }

    public String getAuthToken() {
        return sharedPreferences.getString("AuthToken", "");
    }

    public void setAuthToken(String flag) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("AuthToken", flag);
        editor.apply();
    }
}
