package com.itbeebd.cesc_nsl.utils.dummy;

import java.io.Serializable;

public class ClassAttendance implements Serializable {

    private int studentId;
    private String name;
    private String roll;
    private boolean isPresent;

    public ClassAttendance(){
        isPresent = false;
    }

    public ClassAttendance(String name, String roll) {
        this.name = name;
        this.roll = roll;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getRoll() {
        return roll;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent() {
        isPresent = !isPresent;
    }
}
