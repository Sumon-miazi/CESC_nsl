package com.itbeebd.cesc_nsl.dao;

import android.content.Context;

import com.itbeebd.cesc_nsl.sugarClass.Coordinator;
import com.itbeebd.cesc_nsl.sugarClass.Guardian;
import com.itbeebd.cesc_nsl.sugarClass.Student;
import com.itbeebd.cesc_nsl.sugarClass.Teacher;
import com.itbeebd.cesc_nsl.sugarClass.TeacherClass;
import com.itbeebd.cesc_nsl.sugarClass.TeacherSections;
import com.itbeebd.cesc_nsl.sugarClass.TeacherSubjects;

import java.util.ArrayList;
import java.util.List;

public class TeacherDao {

    public void saveTeacher(Teacher teacher){
        try {
            Teacher.deleteAll(Teacher.class);
        }
        catch (Exception ignore){}

        teacher.save();

    }


    public Teacher getTeacher(Context context) {
        List<Teacher> teachers = Teacher.find(Teacher.class, "EMAIL = ?", CustomSharedPref.getInstance(context).getUserId());
        return teachers == null? null : teachers.size() == 0? null : teachers.get(0);
    }

    public void saveCoordinator(ArrayList<Coordinator> coordinators){
        try {
            Coordinator.deleteAll(Coordinator.class);

            for(int i = 0; i < coordinators.size(); i++){
                coordinators.get(i).save();
            }
        }
        catch (Exception ignore){
            ignore.printStackTrace();
            for(int i = 0; i < coordinators.size(); i++){
                coordinators.get(i).save();
            }
        }

    }

    public void saveTeacherClasses(ArrayList<TeacherClass> teacherClasses){
        try {
            TeacherClass.deleteAll(TeacherClass.class);

            for(int i = 0; i < teacherClasses.size(); i++){
                teacherClasses.get(i).save();
            }
        }
        catch (Exception ignore){
            ignore.printStackTrace();
            for(int i = 0; i < teacherClasses.size(); i++){
                teacherClasses.get(i).save();
            }
        }

    }

    public String[] getAllClasses(){

        List<TeacherClass> classes = TeacherClass.listAll(TeacherClass.class);

        String[] classList = new String[classes.size()];

        for(int i = 0; i < classes.size(); i++){
            classList[i] = classes.get(i).getName();
        }

        return classList;
    }

    public int getClassIdByName(String name){
        List<TeacherClass> classes = TeacherClass.find(TeacherClass.class, "NAME = ?", name);
        return classes == null? 0 : classes.size() == 0? 0 : Integer.parseInt(String.valueOf(classes.get(0).getId()));
    }

    public int getSectionIdByName(String name){
        List<TeacherSections> sections = TeacherSections.find(TeacherSections.class, "NAME = ?", name);
        return sections == null? 0 : sections.size() == 0? 0 : Integer.parseInt(String.valueOf(sections.get(0).getId()));
    }

    public String[] getAllSectionByClassName(String className){

        List<TeacherSections> sections = TeacherSections.find(TeacherSections.class, "CLASS_ID = ?", String.valueOf(getClassIdByName(className)));

        String[] sectionList = new String[sections.size()];

        for(int i = 0; i < sections.size(); i++){
            sectionList[i] = sections.get(i).getName();
        }

        return sectionList;
    }

    public String[] getAllSubjectByClassName(String className){

        List<TeacherSubjects> sections = TeacherSubjects.find(TeacherSubjects.class, "CLASS_ID = ?", String.valueOf(getClassIdByName(className)));

        String[] sectionList = new String[sections.size()];

        for(int i = 0; i < sections.size(); i++){
            sectionList[i] = sections.get(i).getName();
        }

        return sectionList;
    }


    public ArrayList<Student> getAllGuidedStudentByClassSectionId(int classId, int sectionId){
        return (ArrayList<Student>) Student.find(Student.class, "STDCLASSID = ? and SECTIONID = ?", String.valueOf(classId), String.valueOf(sectionId));
    }


    public void saveTeacherSubjects(ArrayList<TeacherSubjects> teacherSubjects){
        try {
            TeacherSubjects.deleteAll(TeacherSubjects.class);

            for(int i = 0; i < teacherSubjects.size(); i++){
                teacherSubjects.get(i).save();
            }
        }
        catch (Exception ignore){
            ignore.printStackTrace();
            for(int i = 0; i < teacherSubjects.size(); i++){
                teacherSubjects.get(i).save();
            }
        }

    }

    public void saveTeacherSections(ArrayList<TeacherSections> teacherSections){
        try {
            TeacherSections.deleteAll(TeacherSections.class);

            for(int i = 0; i < teacherSections.size(); i++){
                teacherSections.get(i).save();
            }
        }
        catch (Exception ignore){
            ignore.printStackTrace();
            for(int i = 0; i < teacherSections.size(); i++){
                teacherSections.get(i).save();
            }
        }

    }

    public void clearGuidedStudentDetails(){
        try {
            Student.deleteAll(Student.class);
            Guardian.deleteAll(Guardian.class);
        }
        catch (Exception ignore){}
    }

}
