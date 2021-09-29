package com.itbeebd.cesc_nsl.utils.dummy;

import java.io.Serializable;

public class Attendance implements Serializable {
    private int present;
    private int absent;

    public Attendance(int present, int absent) {
        this.present = present;
        this.absent = absent;
    }

    public int getPresent() {
        return present;
    }

    public int getAbsent() {
        return absent;
    }
}
