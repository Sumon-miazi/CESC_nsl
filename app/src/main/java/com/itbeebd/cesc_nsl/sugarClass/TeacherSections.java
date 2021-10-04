package com.itbeebd.cesc_nsl.sugarClass;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.io.Serializable;

public class TeacherSections extends SugarRecord implements Serializable {

    @SerializedName("std_class_id")
    private String classId;

    @SerializedName("section_id")
    private int sectionId;

    @SerializedName("name")
    private String name;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
