package com.itbeebd.cesc_nsl.utils;

import com.itbeebd.cesc_nsl.sugarClass.Book;
import com.itbeebd.cesc_nsl.sugarClass.NotificationObj;
import com.itbeebd.cesc_nsl.utils.dummy.Attendance;
import com.itbeebd.cesc_nsl.utils.dummy.ClassRoutine;
import com.itbeebd.cesc_nsl.utils.dummy.LessonPlan;

import java.io.Serializable;
import java.util.ArrayList;

public class DashboardHeaderObj implements Serializable {
    private String totalNotifications;
    private ArrayList<NotificationObj> notificationObjArrayList;

    private final String totalOnlineClass;

    private final String totalLessonPlan;
    private ArrayList<LessonPlan> lessonPlanArrayList;

    private final String totalQuiz;
    private final String totalQuizArchive;

    private Attendance attendance;
    private ArrayList<ClassRoutine> classRoutineArrayList;

    private ArrayList<Book> bookArrayList;

    public DashboardHeaderObj(String totalNotifications, String totalOnlineClass, String totalLessonPlan, String totalQuiz, String totalQuizArchive) {
        this.totalNotifications = totalNotifications;
        this.totalOnlineClass = totalOnlineClass;
        this.totalLessonPlan = totalLessonPlan;
        this.totalQuiz = totalQuiz;
        this.totalQuizArchive = totalQuizArchive;
    }

    public void setTotalNotifications(String totalNotifications) {
        this.totalNotifications = totalNotifications;
    }

    public String getLibraryBookTotal(){
        return String.valueOf(this.bookArrayList.size());
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public void setNotificationObjArrayList(ArrayList<NotificationObj> notificationObjArrayList) {
        this.notificationObjArrayList = notificationObjArrayList;
    }

    public void setLessonPlanArrayList(ArrayList<LessonPlan> lessonPlanArrayList) {
        this.lessonPlanArrayList = lessonPlanArrayList;
    }

    public void setClassRoutineArrayList(ArrayList<ClassRoutine> classRoutineArrayList) {
        this.classRoutineArrayList = classRoutineArrayList;
    }

    public String getTotalNotifications() {
        return totalNotifications;
    }

    public ArrayList<NotificationObj> getNotificationObjArrayList() {
        return notificationObjArrayList;
    }

    public String getTotalOnlineClass() {
        return totalOnlineClass;
    }

    public String getTotalLessonPlan() {
        return totalLessonPlan;
    }

    public ArrayList<LessonPlan> getLessonPlanArrayList() {
        return lessonPlanArrayList;
    }

    public String getTotalQuiz() {
        return totalQuiz;
    }

    public String getTotalQuizArchive() {
        return totalQuizArchive;
    }

    public ArrayList<ClassRoutine> getClassRoutineArrayList() {
        return classRoutineArrayList;
    }

    public ArrayList<Book> getBookArrayList() {
        return bookArrayList;
    }

    public void setBookArrayList(ArrayList<Book> bookArrayList) {
        this.bookArrayList = bookArrayList;
    }
}
