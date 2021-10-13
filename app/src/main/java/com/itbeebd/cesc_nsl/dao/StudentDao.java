package com.itbeebd.cesc_nsl.dao;

import android.content.Context;

import com.itbeebd.cesc_nsl.sugarClass.Guardian;
import com.itbeebd.cesc_nsl.sugarClass.Student;
import com.itbeebd.cesc_nsl.sugarClass.Transport;

import java.util.List;

public class StudentDao {

    public void saveStudent(Student student){
        try {
            Student.deleteAll(Student.class);
        }
        catch (Exception ignore){}

        student.save();

    }

    public Student getStudent(Context context) {
        System.out.println("user Id >>>>> " + CustomSharedPref.getInstance(context).getUserId());

        List<Student> students = Student.find(Student.class, "STUDENT_ID = ?", CustomSharedPref.getInstance(context).getUserId());
        System.out.println("students.size() >>>>> " + students.size());
        return students == null? null : students.size() == 0? null : students.get(0);
    }

    public Transport getTransport(Student student) {
        List<Transport> transports = Transport.find(Transport.class, "STUDENT = ?", String.valueOf(student.getId()));
        return transports == null? null : transports.size() == 0? null : transports.get(0);
    }

    public Guardian getGuardian(Student student, String relation) {
        List<Guardian> guardians = Guardian.find(Guardian.class, "RELATION = ? and STUDENT = ?", relation, String.valueOf(student.getId()));
//        System.out.println("<<<<<<<<<< " + student.getId());
//        System.out.println("<<<<<<<<<< " + relation);
//        System.out.println("<<<<<<<<<< " + guardians.size());
        return guardians == null? null : guardians.size() == 0? null : guardians.get(0);
    }

    public void saveTransport(Transport transport) {
        try {
            Transport.deleteAll(Transport.class);
        }
        catch (Exception ignore){}
        transport.save();
    }

    public void saveGuardian(Guardian guardian, String relation) {
        try {
            List<Guardian> guardians = Guardian.find(Guardian.class, "RELATION = ?", relation);
            for(int i = 0; i < guardians.size(); i++){
                guardians.get(i).delete();
            }
        }
        catch (Exception ignore){}
        guardian.save();
    }
}
