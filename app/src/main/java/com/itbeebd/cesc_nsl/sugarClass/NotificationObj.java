package com.itbeebd.cesc_nsl.sugarClass;

import com.orm.SugarRecord;

import java.io.Serializable;

public class NotificationObj extends SugarRecord implements Serializable {

    private int n_id;
    private String title;
    private String body;

    public NotificationObj() {

    }

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
