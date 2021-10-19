package com.itbeebd.cesc_nsl.api.teacherApi;

import android.content.Context;

import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;
import com.itbeebd.cesc_nsl.dao.TeacherDao;
import com.itbeebd.cesc_nsl.interfaces.BooleanResponse;
import com.itbeebd.cesc_nsl.utils.CustomProgressDialog;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationApi  extends BaseService {
    private final Context context;
    private final RetrofitRequestBody requestBody;
    private final CustomProgressDialog progressDialog;
    private final TeacherDao teacherDao;

    public NotificationApi(Context context, String message){
        this.context = context;
        this.teacherDao = new TeacherDao();
        requestBody = new RetrofitRequestBody();
        this.progressDialog = new CustomProgressDialog(context, message);
    }

    public void sendNotification(String token, String title, String message, int selectedClassId, int selectedSectionId, BooleanResponse booleanResponse){
        progressDialog.show();
        Call<ResponseBody> studentLogout = service.getRequestPath(token, ApiUrls.T_SEND_NOTIFICATION, requestBody.sendNotification(title, message, selectedClassId, selectedSectionId));
        studentLogout.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONObject jsonObject = null;
                if(response.isSuccessful() && response != null){
                    System.out.println(">>>>>>>>>> " + response.body());
                    try {
                        jsonObject =  new JSONObject(response.body().string());
                        System.out.println(">>>>>>>>>> " + jsonObject);
                        booleanResponse.response(jsonObject.optBoolean("isSuccessful"), jsonObject.optString("message"));

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>> catch " + e.fillInStackTrace());
                        booleanResponse.response(false, e.getLocalizedMessage());
                    }


                }
                else {
                    booleanResponse.response(response.isSuccessful(), response.toString());
                    System.out.println(">>>>>>>>>> " + response.isSuccessful());
                    System.out.println(">>>>>>>>>> " + response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                booleanResponse.response(false, t.getLocalizedMessage());
                System.out.println(">>>>>>>>>> " + t.getLocalizedMessage());
            }
        });
    }
}
