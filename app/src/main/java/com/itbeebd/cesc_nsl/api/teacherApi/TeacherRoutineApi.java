package com.itbeebd.cesc_nsl.api.teacherApi;

import android.content.Context;

import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;
import com.itbeebd.cesc_nsl.dao.TeacherDao;
import com.itbeebd.cesc_nsl.interfaces.ResponseObj;
import com.itbeebd.cesc_nsl.utils.CustomProgressDialog;
import com.itbeebd.cesc_nsl.utils.dummy.TeacherRoutine;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherRoutineApi extends BaseService {
    private final Context context;
    private final RetrofitRequestBody requestBody;
    private final CustomProgressDialog progressDialog;
    private final TeacherDao teacherDao;

    public TeacherRoutineApi(Context context, String message){
        this.context = context;
        this.teacherDao = new TeacherDao();
        requestBody = new RetrofitRequestBody();
        this.progressDialog = new CustomProgressDialog(context, message);
    }

    public void getTeacherRoutine(String token, ResponseObj responseObj){
        progressDialog.show();
        Call<ResponseBody> studentByClassSectionCall = service.getRequestPath(token, ApiUrls.TEACHER_ROUTINE);
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

                        if(jsonArray.length() == 0) {
                            responseObj.data(null, "No routine found!");
                            return;
                        }



                        Map<String, ArrayList<TeacherRoutine>> weeklyRoutine = new HashMap<>();

                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            JSONArray details = object.getJSONArray("details");
                            ArrayList<TeacherRoutine> teacherRoutines = new ArrayList<>();
                            for(int j = 0; j < details.length(); j++){
                                JSONObject obj = details.getJSONObject(j);
                                if(obj.optInt("status") == 1){
                                    TeacherRoutine teacherRoutine = new TeacherRoutine(
                                        obj.optString("class") + "(" + obj.optString("section") + ")",
                                        obj.optString("subject"),
                                            obj.getJSONObject("duration").optString("winter_start") + " " + obj.getJSONObject("duration").optString("winter_end"),
                                            obj.getJSONObject("duration").optString("start") + " " + obj.getJSONObject("duration").optString("end")
                                    );

                                    teacherRoutines.add(teacherRoutine);

                                }
                            }

                            weeklyRoutine.put(object.optString("day"), teacherRoutines);
                        }

                        responseObj.data(weeklyRoutine, "Successful");
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
                System.out.println(">>>>>>>>>> getTeacherRoutine " + t.getLocalizedMessage());
                responseObj.data(null, t.getLocalizedMessage());

            }
        });
    }


}
