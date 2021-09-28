package com.itbeebd.cesc_nsl.dao;

import android.content.Context;

import com.itbeebd.cesc_nsl.sugarClass.Guardian;
import com.itbeebd.cesc_nsl.sugarClass.Student;
import com.itbeebd.cesc_nsl.sugarClass.Transport;

import java.util.List;

public class StudentDao {

    public Student getStudent(Context context) {
        List<Student> students = Student.find(Student.class, "STUDENT_ID = ?", CustomSharedPref.getInstance(context).getUserId());
        return students == null? null : students.size() == 0? null : students.get(0);
    }

    public Transport getTransport(Student student) {
        List<Transport> transports = Transport.find(Transport.class, "STUDENT = ?", String.valueOf(student.getId()));
        return transports == null? null : transports.size() == 0? null : transports.get(0);
    }

    public Guardian getGuardian(Student student, String relation) {
        List<Guardian> guardians = Guardian.find(Guardian.class, "RELATION = ? and STUDENT = ?", relation, String.valueOf(student.getId()));
        System.out.println("<<<<<<<<<< " + student.getStudentId());
        System.out.println("<<<<<<<<<< " + relation);
        System.out.println("<<<<<<<<<< " + guardians);
        return guardians == null? null : guardians.size() == 0? null : guardians.get(0);
    }

}
