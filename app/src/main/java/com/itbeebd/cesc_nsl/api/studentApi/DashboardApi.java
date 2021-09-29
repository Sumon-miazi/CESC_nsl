package com.itbeebd.cesc_nsl.api.studentApi;

import android.content.Context;

import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;
import com.itbeebd.cesc_nsl.dao.NotificationDao;
import com.itbeebd.cesc_nsl.interfaces.ResponseObj;
import com.itbeebd.cesc_nsl.sugarClass.Book;
import com.itbeebd.cesc_nsl.sugarClass.NotificationObj;
import com.itbeebd.cesc_nsl.utils.CustomProgressDialog;
import com.itbeebd.cesc_nsl.utils.DashboardHeaderObj;
import com.itbeebd.cesc_nsl.utils.dummy.Attendance;
import com.itbeebd.cesc_nsl.utils.dummy.ClassRoutine;
import com.itbeebd.cesc_nsl.utils.dummy.LessonFile;
import com.itbeebd.cesc_nsl.utils.dummy.LessonPlan;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardApi extends BaseService {

    private Context context;
    final RetrofitRequestBody requestBody;
    private CustomProgressDialog progressDialog;

    public DashboardApi(Context context) {
        this.context = context;
        requestBody = new RetrofitRequestBody();
        this.progressDialog = new CustomProgressDialog(context, "Loading...");
    }

    public void getDashboardHeaderInfo(String token, ResponseObj responseObj){
        progressDialog.show();
        Call<ResponseBody> dashboardHeaderInfo = service.getRequestPath(token, ApiUrls.DASHBOARD_HEADER_INFO);
        dashboardHeaderInfo.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONObject data = null;
                if(response.isSuccessful() && response != null){

                    try {
                        data =  new JSONObject(response.body().string());

                        System.out.println(">>>>>>>>>> data " + data);

                        JSONObject attendanceJsonObj =  data.getJSONObject("attendance");
                        JSONObject lessonPlanJsonObj =  data.getJSONObject("lessonPlan");
                        JSONArray lessonPlanJsonArray =  lessonPlanJsonObj.getJSONArray("lessonPlan");
                        JSONObject notificationJsonObj =  data.getJSONObject("notification");
                        JSONArray notificationJsonArray =  notificationJsonObj.getJSONArray("notification");
                        JSONArray classRoutineJsonArray =  data.getJSONArray("classRoutine");
                        JSONArray libraryBookJsonArray =  data.getJSONArray("libraryBooks");

                        System.out.println(">>>>>>>>>> dashboardHeaderInfo " + data.optInt("totalOnlineClass"));

                        DashboardHeaderObj headerObj = new DashboardHeaderObj(
                                String.valueOf(notificationJsonObj.optInt("totalNotification")),
                                String.valueOf(data.optInt("totalOnlineClass")),
                                String.valueOf(lessonPlanJsonObj.optInt("total_lesson")),
                                String.valueOf(data.optInt("totalQuiz")),
                                String.valueOf(data.optInt("totalQuizArchive"))
                        );

                        ArrayList<ClassRoutine> classRoutineArrayList = new ArrayList<>();
                        for (int i = 0; i < classRoutineJsonArray.length(); i++){
                            JSONObject object = classRoutineJsonArray.getJSONObject(i);

                            if(object.optInt("status") == 1){
                                ClassRoutine classRoutine = new ClassRoutine(
                                        object.optString("subject"),
                                        object.optString("teacher"),
                                        object.getJSONObject("duration").optString("winter_start") + " " + object.getJSONObject("duration").optString("winter_end"),
                                        object.getJSONObject("duration").optString("start") + " " + object.getJSONObject("duration").optString("end")
                                );

                                classRoutineArrayList.add(classRoutine);
                            }
                        }

                        ArrayList<LessonPlan> lessonPlanArrayList = new ArrayList<>();

                        for(int i = 0; i < lessonPlanJsonArray.length(); i++) {
                            JSONObject object = lessonPlanJsonArray.getJSONObject(i);

                            LessonPlan lp = new LessonPlan(
                                    object.getJSONObject("teacher").optString("name"),
                                    object.getJSONObject("subject").optString("name"),
                                    object.optString("title"),
                                    object.optString("updated_at")
                            );

                            JSONArray fileObjectJsonArray = object.getJSONArray("teacher_upload_file_details");
                            ArrayList<LessonFile> lessonFileArrayList = new ArrayList<>();

                            for(int j = 0; j < fileObjectJsonArray.length(); j++) {
                                JSONObject fileObject = fileObjectJsonArray.getJSONObject(j);
                                LessonFile lessonFile = new LessonFile(
                                        ApiUrls.BASE_IMAGE_URL + fileObject.optString("file"),
                                        fileObject.optString("original_name")
                                );
                                lessonFileArrayList.add(lessonFile);
                            }
                            lp.setLessonFileArrayList(lessonFileArrayList);
                            lessonPlanArrayList.add(lp);
                        }


                        Attendance attendanceObj = new Attendance(
                                attendanceJsonObj.optInt("present"),
                                attendanceJsonObj.optInt("absend")
                        );

                        ArrayList<NotificationObj> notificationObjArrayList = new ArrayList<>();
                        for(int i = 0; i < notificationJsonArray.length(); i++) {
                            JSONObject object = notificationJsonArray.getJSONObject(i);

                            NotificationObj notificationObj = new NotificationObj(
                                    object.getInt("id"),
                                    object.optString("title"),
                                    object.optString("body")
                            );
                            notificationObjArrayList.add(notificationObj);
                        }

                        ArrayList<Book> bookArrayList = new ArrayList<>();
                        for(int i = 0; i < libraryBookJsonArray.length(); i++) {
                            JSONObject object = libraryBookJsonArray.getJSONObject(i);

                            Book book = new Book(
                                    ApiUrls.BASE_IMAGE_URL + object.optString("book"),
                                    object.optString("image"),
                                    object.optString("title"),
                                    object.optString("author")
                            );
                            bookArrayList.add(book);
                        }
                        try {
                            headerObj.setTotalNotifications(new NotificationDao().saveNotification(notificationObjArrayList));
                        }
                        catch (Exception ignore){}

                        Collections.reverse(notificationObjArrayList);

                        headerObj.setAttendance(attendanceObj);
                        headerObj.setClassRoutineArrayList(classRoutineArrayList);
                        headerObj.setLessonPlanArrayList(lessonPlanArrayList);
                        headerObj.setNotificationObjArrayList(notificationObjArrayList);
                        headerObj.setBookArrayList(bookArrayList);
                        responseObj.data(headerObj, "successful");

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>> due catch " + e.fillInStackTrace());

                    responseObj.data(null, e.getLocalizedMessage());
                    }


                }
                else {
                responseObj.data(null, response.message());
                    System.out.println(">>>>>>>>>> " + response.isSuccessful());
                    System.out.println(">>>>>>>>>> " + response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(">>>>>>>>>> " + t.getLocalizedMessage());
            responseObj.data(null, t.getLocalizedMessage());
            }
        });
    }

}
