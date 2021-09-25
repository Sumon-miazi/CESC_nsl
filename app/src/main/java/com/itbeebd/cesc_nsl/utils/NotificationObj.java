package com.itbeebd.cesc_nsl.utils;

import java.io.Serializable;

public class NotificationObj implements Serializable {

    private int n_id;
    private String title;
    private String body;

    public NotificationObj(int n_id, String title, String body) {
        this.n_id = n_id;
        this.title = title;
        this.body = body;
    }

    public int getN_id() {
        return n_id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
