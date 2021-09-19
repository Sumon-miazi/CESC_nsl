package com.itbeebd.cesc_nsl.utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;

import com.andrognito.flashbar.Flashbar;
import com.itbeebd.cesc_nsl.R;


public class FlashBar {

    public Flashbar flashBar(Activity activity, String title, String message, int duration){
        return new Flashbar.Builder(activity)
                .gravity(Flashbar.Gravity.TOP)
                .title(title)
                .message(message)
                .duration(duration)
                .backgroundColorRes(R.color.design_default_color_primary_dark)
                .build();
    }
}
