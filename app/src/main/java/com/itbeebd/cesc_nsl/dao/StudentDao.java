package com.itbeebd.cesc_nsl.dao;

import android.content.Context;

import com.itbeebd.cesc_nsl.sugarClass.Guardian;
import com.itbeebd.cesc_nsl.sugarClass.Student;
import com.itbeebd.cesc_nsl.sugarClass.Transport;

import java.util.ArrayList;
import java.util.List;

public class StudentDao {

    public Student getStudent(Context context) {
        List<Student> students = Student.find(Student.class, "STUDENT_ID = ?", CustomSharedPref.getInstance(context).getUserId());
        return students == null? null : students.get(0);
    }

    public Transport getTransport(Student student) {
        List<Transport> transports = Transport.find(Transport.class, "STUDENT = ?", String.valueOf(student.getId()));
        return transports == null? null : transports.get(0);
    }

    public List<Guardian> getGuardian(Student student) {
        return Guardian.find(Guardian.class, "STUDENT = ?", String.valueOf(student.getId()));
    }
}