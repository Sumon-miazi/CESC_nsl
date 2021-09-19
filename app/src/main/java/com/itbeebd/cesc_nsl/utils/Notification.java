package com.itbeebd.cesc_nsl.utils;

import android.os.Parcelable;

import java.io.Serializable;

public class Notification implements Serializable {

    private String title;
    private String body;

    public Notification(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
