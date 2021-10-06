package com.itbeebd.cesc_nsl.utils.dummy;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClassAttendance implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("studentid")
    private int studentid;

    @SerializedName("name")
    private String name;

    @SerializedName("roll")
    private int roll;

    @SerializedName("std_class_id")
    private String std_class_id;

    @SerializedName("section_id")
    private int section_id;

    private boolean isPresent;

    public ClassAttendance() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public int getStudentid() {
        return studentid;
    }

    public void setStudentid(int studentid) {
        this.studentid = studentid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public String getStd_class_id() {
        return std_class_id;
    }

    public void setStd_class_id(String std_class_id) {
        this.std_class_id = std_class_id;
    }

    public int getSection_id() {
        return section_id;
    }

    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }

    public void setPresent() {
        isPresent = !isPresent;
    }

    public void setPresent(boolean b) {
        isPresent = b;
    }

    public void setPresent(int b) {
        isPresent = b == 1;
    }
}
