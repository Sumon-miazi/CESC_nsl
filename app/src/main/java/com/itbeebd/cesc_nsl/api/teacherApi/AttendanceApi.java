package com.itbeebd.cesc_nsl.api.teacherApi;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;
import com.itbeebd.cesc_nsl.dao.TeacherDao;
import com.itbeebd.cesc_nsl.interfaces.BooleanResponse;
import com.itbeebd.cesc_nsl.interfaces.ResponseObj;
import com.itbeebd.cesc_nsl.utils.CustomProgressDialog;
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
        Call<ResponseBody> attendanceCall = service.attendance(token, attendances);
        attendanceCall.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONObject jsonObject = null;
                if(response.isSuccessful() && response != null){
                    System.out.println(">>>>>>>>>> " + response.body());
                    try {
                        jsonObject =  new JSONObject(response.body().string());
                        System.out.println(">>>>>>>>>> " + jsonObject);

                        booleanResponse.response(true, jsonObject.toString());
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
}
