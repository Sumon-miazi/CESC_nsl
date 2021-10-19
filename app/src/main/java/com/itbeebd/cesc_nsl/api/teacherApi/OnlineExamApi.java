package com.itbeebd.cesc_nsl.api.teacherApi;

import android.content.Context;

import com.google.gson.JsonObject;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;
import com.itbeebd.cesc_nsl.interfaces.BooleanResponse;
import com.itbeebd.cesc_nsl.utils.CustomProgressDialog;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnlineExamApi extends BaseService {
    private Context context;
    final RetrofitRequestBody requestBody;
    private CustomProgressDialog progressDialog;

    public OnlineExamApi(Context context, String message) {
        this.context = context;
        requestBody = new RetrofitRequestBody();
        this.progressDialog = new CustomProgressDialog(context, message);
    }

    public void addOnlineExam(String token, JsonObject attendances, BooleanResponse booleanResponse){
        progressDialog.show();
        Call<ResponseBody> attendanceCall = service.serviceWithJsonObject(token, ApiUrls.ADD_LIVE_QUIZ ,attendances);
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
}
