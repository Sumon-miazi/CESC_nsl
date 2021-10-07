package com.itbeebd.cesc_nsl.utils.dummy;

import com.google.gson.annotations.SerializedName;
import com.itbeebd.cesc_nsl.dao.TeacherDao;
import com.itbeebd.cesc_nsl.sugarClass.TeacherSections;

import java.util.ArrayList;

public class TeacherLessonPlan {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("std_class_id")
    private int classId;

    @SerializedName("subject_id")
    private int subjectId;

    private ArrayList<TeacherSections> sections;

    private ArrayList<LessonFile> files;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public ArrayList<TeacherSections> getSections() {
        return sections;
    }

    public void setSections(ArrayList<TeacherSections> sections) {
        this.sections = sections;
    }

    public ArrayList<LessonFile> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<LessonFile> files) {
        this.files = files;
    }

    public String getClassName(){
        return new TeacherDao().getClassNameById(this.classId);
    }

    public String getSubjectName(){
        return new TeacherDao().getSubjectNameById(this.subjectId).split("-")[1];
    }

    public String getSectionsName() {
       String sectionName = "";

       for(int i = 0; i < this.sections.size(); i++){
           sectionName += sections.get(i).getName() + "\n";
       }

       return sectionName.substring(0, sectionName.length() > 1?  sectionName.length() - 1 : sectionName.length());
    }
}
