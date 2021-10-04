package com.itbeebd.cesc_nsl.sugarClass;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.io.Serializable;

public class TeacherSubjects extends SugarRecord implements Serializable {

    @SerializedName("std_class_id")
    private int classId;

    @SerializedName("name")
    private String name;

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
