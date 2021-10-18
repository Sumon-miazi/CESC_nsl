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

    public void setUserLoggedInOrNot(boolean flag){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("StudentLoggedInOrNot", flag);
        editor.apply();
    }

    public boolean getUserLoggedInOrNot(){
        return sharedPreferences.getBoolean("StudentLoggedInOrNot", false);
    }


    // user student or teacher, id and password
    public String getUserType() {
        return sharedPreferences.getString("UserType", "");
    }

    public void setUserType(String flag) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserType", flag);
        editor.apply();
    }

    public String getUserId() {
        return sharedPreferences.getString("UserId", "");
    }

    public void setUserId(String flag) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserId", flag);
        editor.apply();
    }

    public String getUserPassword() {
        return sharedPreferences.getString("UserPassword", "");
    }

    public void setUserPassword(String flag) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserPassword", flag);
        editor.apply();
    }

    // firebase notification token
    public String getNotificationToken() {
        return sharedPreferences.getString("NotificationToken", "");
    }

    public void setNotificationToken(String flag) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("NotificationToken", flag);
        editor.apply();
    }


    // user auth token
    public String getAuthToken() {
        return sharedPreferences.getString("AuthToken", "");
    }

    public void setAuthToken(String flag) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("AuthToken", flag);
        editor.apply();
    }




    public int getUpdateRequest() {
        return sharedPreferences.getInt("UpdateRequest", 0);
    }

    public void setUpdateRequest(int flag) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("UpdateRequest", flag);
        editor.apply();
    }

    public boolean getPayNowClicked() {
        return sharedPreferences.getBoolean("PayNowClicked", false);
    }

    public void setPayNowClicked(boolean flag) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("PayNowClicked", flag);
        editor.apply();
    }


    public int getUserModeNo() {
        return sharedPreferences.getInt("UserModeNo", 1);
    }

    public void setUserModeNo(int flag) {
        /*
        UserModeNo = 1 [user viewing attendance]
        UserModeNo = 2 [user editing attendance]
         */
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("UserModeNo", flag);
        editor.apply();
    }
}
