package com.itbeebd.cesc_nsl.dao;

import android.content.Context;

import com.itbeebd.cesc_nsl.sugarClass.Coordinator;
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
        List<Teacher> teachers = Teacher.find(Teacher.class, "TEACHER_ID = ?", CustomSharedPref.getInstance(context).getUserId());
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
}
