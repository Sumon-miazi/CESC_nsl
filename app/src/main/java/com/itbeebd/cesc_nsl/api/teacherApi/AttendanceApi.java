package com.itbeebd.cesc_nsl.api.teacherApi;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;
import com.itbeebd.cesc_nsl.dao.TeacherDao;
import com.itbeebd.cesc_nsl.interfaces.BooleanResponse;
import com.itbeebd.cesc_nsl.interfaces.ResponseObj;
import com.itbeebd.cesc_nsl.utils.CustomProgressDialog;
import com.itbeebd.cesc_nsl.utils.dummy.AbsentFeeObject;
import com.itbeebd.cesc_nsl.utils.dummy.AttendanceList;
import com.itbeebd.cesc_nsl.utils.dummy.ClassAttendance;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceApi extends BaseService {
    private final Context context;
    private final RetrofitRequestBody requestBody;
    private final CustomProgressDialog progressDialog;
    private final TeacherDao teacherDao;

    public AttendanceApi(Context context, String message){
        this.context = context;
        this.teacherDao = new TeacherDao();
        requestBody = new RetrofitRequestBody();
        this.progressDialog = new CustomProgressDialog(context, message);
    }

    public void studentAttendance(String token, JsonObject attendances, BooleanResponse booleanResponse){
        progressDialog.show();
        Call<ResponseBody> attendanceCall = service.serviceWithJsonObject(token, ApiUrls.STUDENT_ATTENDANCE ,attendances);
        attendanceCall.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONObject jsonObject = null;
                if(response.isSuccessful() && response != null){
                    System.out.println(">>>>>>>>>> studentAttendance " + response.body());
                    try {
                        jsonObject =  new JSONObject(response.body().string());
                        System.out.println(">>>>>>>>>> studentAttendance " + jsonObject);

                        booleanResponse.response(jsonObject.optBoolean("isSuccessful"), jsonObject.optString("message"));
                     //   booleanResponse.response(jsonObject.optBoolean("isSuccessful"), jsonObject.optString("message"));

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>> catch " + e.fillInStackTrace());

                        booleanResponse.response(false, e.getLocalizedMessage());
                    }
                }
                else {
                    System.out.println(">>>>>>>>>> " + response.isSuccessful());
                    System.out.println(">>>>>>>>>> " + response);
                    booleanResponse.response(response.isSuccessful(), response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(">>>>>>>>>> studentAttendance " + t.getLocalizedMessage());
                booleanResponse.response(false, t.getLocalizedMessage());

            }
        });
    }

    public void getStudentByClassSectionId(String token, int classId, int sectionId, ResponseObj responseObj){
        progressDialog.show();
        Call<ResponseBody> studentByClassSectionCall = service.getStudentForAttendance(token, classId, sectionId);
        studentByClassSectionCall.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONArray jsonArray = null;
                if(response.isSuccessful() && response != null){
                    System.out.println(">>>>>>>>>> " + response.body());
                    try {
                        jsonArray =  new JSONArray(response.body().string());
                        System.out.println(">>>>>>>>>> " + jsonArray);

                        if(jsonArray.length() == 0) return;

                        Gson gson = new Gson();
                        ArrayList<ClassAttendance> classAttendances = new ArrayList<>();

                        for(int i = 0; i < jsonArray.length(); i++){
                            ClassAttendance classAttendance = gson.fromJson(jsonArray.getJSONObject(i).toString(), ClassAttendance.class);
                            classAttendance.setPresent(true);
                            classAttendances.add(classAttendance);
                        }

                        responseObj.data(classAttendances, "Successful");
                      //  booleanResponse.response(true, jsonObject.toString());
                        //   booleanResponse.response(jsonObject.optBoolean("isSuccessful"), jsonObject.optString("message"));

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>> catch " + e.fillInStackTrace());

                        responseObj.data(null, e.getLocalizedMessage());
                    }
                }
                else {
                    System.out.println(">>>>>>>>>> " + response.isSuccessful());
                    System.out.println(">>>>>>>>>> " + response);
                    responseObj.data(null, response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(">>>>>>>>>> studentAttendance " + t.getLocalizedMessage());
                responseObj.data(null, t.getLocalizedMessage());

            }
        });
    }

    public void getAttendanceList(String token, int classId, int sectionId, ResponseObj responseObj){
        progressDialog.show();
        Call<ResponseBody> attendanceListCall = service.getRequestPath(token, ApiUrls.ATTENDANCE_LIST, requestBody.attendanceList( classId, sectionId));
        attendanceListCall.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONArray jsonArray = null;
                if(response.isSuccessful() && response != null){
                    System.out.println(">>>>>>>>>> " + response.body());
                    try {
                        jsonArray =  new JSONArray(response.body().string());
                        System.out.println(">>>>>>>>>> " + jsonArray);

                        if(jsonArray.length() == 0){
                            responseObj.data(null, "No attendance found!");
                            return;
                        }

                        Gson gson = new Gson();
                        ArrayList<AttendanceList> attendanceLists = new ArrayList<>();

                        for(int i = 0; i < jsonArray.length(); i++){
                            AttendanceList attendanceList = gson.fromJson(jsonArray.getJSONObject(i).toString(), AttendanceList.class);
                            attendanceLists.add(attendanceList);
                        }

                        responseObj.data(attendanceLists, "Successful");
                        //  booleanResponse.response(true, jsonObject.toString());
                        //   booleanResponse.response(jsonObject.optBoolean("isSuccessful"), jsonObject.optString("message"));

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>> catch " + e.fillInStackTrace());

                        responseObj.data(null, e.getLocalizedMessage());
                    }
                }
                else {
                    System.out.println(">>>>>>>>>> " + response.isSuccessful());
                    System.out.println(">>>>>>>>>> " + response);
                    responseObj.data(null, response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(">>>>>>>>>> studentAttendance " + t.getLocalizedMessage());
                responseObj.data(null, t.getLocalizedMessage());

            }
        });
    }

    public void getAttendanceByAttendanceId(String token, int attendanceId, ResponseObj responseObj){
        progressDialog.show();
        Call<ResponseBody> studentByClassSectionCall = service.getAttendanceByAttendanceId(token, attendanceId);
        studentByClassSectionCall.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONObject data = null;
                if(response.isSuccessful() && response != null){
                    System.out.println(">>>>>>>>>> " + response.body());
                    try {

                        data =  new JSONObject(response.body().string());
                        JSONArray jsonArray =  data.getJSONArray("student_has_attendance");
                        System.out.println(">>>>>>>>>> " + jsonArray);

                        if(jsonArray.length() == 0) return;

                        Gson gson = new Gson();
                        ArrayList<ClassAttendance> classAttendances = new ArrayList<>();

                        for(int i = 0; i < jsonArray.length(); i++){
                            ClassAttendance classAttendance = gson.fromJson(jsonArray.getJSONObject(i).toString(), ClassAttendance.class);
                            classAttendance.setPresent(jsonArray.getJSONObject(i).optInt("present"));
                            System.out.println("present >>>>>>  " + jsonArray.getJSONObject(i).optBoolean("present"));
                            classAttendances.add(classAttendance);
                        }

                        responseObj.data(classAttendances, "Successful");
                        //  booleanResponse.response(true, jsonObject.toString());
                        //   booleanResponse.response(jsonObject.optBoolean("isSuccessful"), jsonObject.optString("message"));

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>> catch " + e.fillInStackTrace());

                        responseObj.data(null, e.getLocalizedMessage());
                    }
                }
                else {
                    System.out.println(">>>>>>>>>> " + response.isSuccessful());
                    System.out.println(">>>>>>>>>> " + response);
                    responseObj.data(null, response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(">>>>>>>>>> studentAttendance " + t.getLocalizedMessage());
                responseObj.data(null, t.getLocalizedMessage());

            }
        });
    }

    public void getAttendanceSummery(String token,
                                     int classId,
                                     int sectionId,
                                     String fromDate,
                                     String toDate,
                                     ResponseObj responseObj){
        progressDialog.show();
        Call<ResponseBody> callObject = service.getRequestPath(
                token,
                ApiUrls.ABSENT_FEE,
                requestBody.attendanceSummery( classId, sectionId, fromDate, toDate)
        );

        callObject.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONArray jsonArray = null;
                if(response.isSuccessful() && response != null){
                    System.out.println(">>>>>>>>>> " + response.body());
                    try {
                        jsonArray =  new JSONArray(response.body().string());
                        System.out.println(">>>>>>>>>> " + jsonArray);
                        System.out.println(">>>>>>>>>> length " + jsonArray.length());

                        if(jsonArray.length() == 0){
                            responseObj.data(null, "No data found!");
                            return;
                        }

                        Gson gson = new Gson();
                        ArrayList<AbsentFeeObject> absentFeeObjects = new ArrayList<>();

                        for(int i = 0; i < jsonArray.length(); i++){
                            AbsentFeeObject absentObj = gson.fromJson(jsonArray.getJSONObject(i).toString(), AbsentFeeObject.class);
                            absentFeeObjects.add(absentObj);
                        }

                        responseObj.data(absentFeeObjects, "Successful");
                        //  booleanResponse.response(true, jsonObject.toString());
                        //   booleanResponse.response(jsonObject.optBoolean("isSuccessful"), jsonObject.optString("message"));

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>> catch " + e.fillInStackTrace());

                        responseObj.data(null, e.getLocalizedMessage());
                    }
                }
                else {
                    System.out.println(">>>>>>>>>> " + response.isSuccessful());
                    System.out.println(">>>>>>>>>> " + response);
                    responseObj.data(null, response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(">>>>>>>>>> studentAttendance " + t.getLocalizedMessage());
                responseObj.data(null, t.getLocalizedMessage());

            }
        });
    }

    public void attendanceFeeApproved(String token, JsonObject absents, BooleanResponse booleanResponse){
        progressDialog.show();
        Call<ResponseBody> attendanceCall = service.serviceWithJsonObject(token, ApiUrls.ABSENT_FEE_APPROVED ,absents);
        attendanceCall.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONObject jsonObject = null;
                if(response.isSuccessful() && response != null){
                    System.out.println(">>>>>>>>>> attendanceFeeApproved " + response.body());
                    try {
                        jsonObject =  new JSONObject(response.body().string());
                        System.out.println(">>>>>>>>>> attendanceFeeApproved " + jsonObject);

                        booleanResponse.response(jsonObject.optBoolean("isSuccessful"), jsonObject.optString("message"));
                        //   booleanResponse.response(jsonObject.optBoolean("isSuccessful"), jsonObject.optString("message"));

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>> catch " + e.fillInStackTrace());

                        booleanResponse.response(false, e.getLocalizedMessage());
                    }
                }
                else {
                    System.out.println(">>>>>>>>>> " + response.isSuccessful());
                    System.out.println(">>>>>>>>>> " + response);
                    booleanResponse.response(response.isSuccessful(), response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(">>>>>>>>>> studentAttendance " + t.getLocalizedMessage());
                booleanResponse.response(false, t.getLocalizedMessage());

            }
        });
    }

}
