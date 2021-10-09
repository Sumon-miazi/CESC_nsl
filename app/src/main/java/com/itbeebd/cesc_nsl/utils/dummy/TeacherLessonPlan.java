package com.itbeebd.cesc_nsl.utils.dummy;

import com.google.gson.annotations.SerializedName;
import com.itbeebd.cesc_nsl.dao.TeacherDao;
import com.itbeebd.cesc_nsl.sugarClass.TeacherSections;

import java.io.Serializable;
import java.util.ArrayList;

public class TeacherLessonPlan implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("std_class_id")
    private int classId;

    @SerializedName("subject_id")
    private int subjectId;

    private int teacher_upload_file_id;
    private ArrayList<String> lessonFiles;
    private ArrayList<String> strSection;


    private ArrayList<TeacherSections> sections;

    private ArrayList<LessonFile> files;

    public String[] getStrSection() {
        String[] sectionList = new String[strSection.size()];

        for(int i = 0; i < sections.size(); i++){
            sectionList[i] = sections.get(i).getName();
        }
        return sectionList;
    }

    public void setStrSection(ArrayList<String> strSection) {
        this.strSection = strSection;
    }

    public int getTeacher_upload_file_id() {
        return teacher_upload_file_id;
    }

    public void setTeacher_upload_file_id(int teacher_upload_file_id) {
        this.teacher_upload_file_id = teacher_upload_file_id;
    }

    public ArrayList<String> getLessonFiles() {
        return lessonFiles;
    }

    public void setLessonFiles(ArrayList<String> lessonFiles) {
        this.lessonFiles = lessonFiles;
    }

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
           String[] data = sections.get(i).getName().split("-");
           sectionName += data[1] + "\n";
       }

       return sectionName.substring(0, sectionName.length() > 1?  sectionName.length() - 1 : sectionName.length());
    }

    public String getSectionsNameHorizontal() {
        String sectionName = "";

        for(int i = 0; i < this.sections.size(); i++){
            sectionName += sections.get(i).getName() + ", ";
        }

        return sectionName.substring(0, sectionName.length() > 2?  sectionName.length() - 2 : sectionName.length());
    }
}
