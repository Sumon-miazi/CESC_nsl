package com.itbeebd.cesc_nsl.sugarClass;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.io.Serializable;

public class Transport extends SugarRecord implements Serializable {

    private Student student;

    @SerializedName("location")
    private String busRoute;

    @SerializedName("bus_time")
    private String busStartTime;

    @SerializedName("title")
    private String busName;

    @SerializedName("model")
    private String busModel;

    public Transport() {

    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getBusRoute() {
        return busRoute;
    }

    public void setBusRoute(String busRoute) {
        this.busRoute = busRoute;
    }

    public String getBusStartTime() {
        return busStartTime;
    }

    public void setBusStartTime(String busStartTime) {
        this.busStartTime = busStartTime;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusModel() {
        return busModel;
    }

    public void setBusModel(String busModel) {
        this.busModel = busModel;
    }
}
