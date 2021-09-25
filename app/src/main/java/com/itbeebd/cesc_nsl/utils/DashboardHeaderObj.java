package com.itbeebd.cesc_nsl.utils;

import java.io.Serializable;
import java.util.ArrayList;

public class DashboardHeaderObj implements Serializable {
    private String totalNotifications;
    private ArrayList<NotificationObj> notificationObjArrayList;

    private String totalOnlineClass;

    private String totalLessonPlan;
    private ArrayList<LessonPlan> lessonPlanArrayList;

    private String totalQuiz;
    private String totalQuizArchive;

    private Attendance attendance;
    private ArrayList<ClassRoutine> classRoutineArrayList;

    public DashboardHeaderObj(String totalNotifications, String totalOnlineClass, String totalLessonPlan, String totalQuiz, String totalQuizArchive) {
        this.totalNotifications = totalNotifications;
        this.totalOnlineClass = totalOnlineClass;
        this.totalLessonPlan = totalLessonPlan;
        this.totalQuiz = totalQuiz;
        this.totalQuizArchive = totalQuizArchive;
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
}
