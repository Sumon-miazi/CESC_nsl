package com.itbeebd.cesc_nsl.utils.dummy;

import com.google.gson.annotations.SerializedName;

public class AttendanceList {

    @SerializedName("id")
    private int id;

    @SerializedName("attendance_date")
    private String date;

    @SerializedName("remarks")
    private String remarks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
