package com.itbeebd.cesc_nsl.api.studentApi;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.interfaces.BooleanResponse;
import com.itbeebd.cesc_nsl.sugarClass.Guardian;
import com.itbeebd.cesc_nsl.sugarClass.Student;
import com.itbeebd.cesc_nsl.sugarClass.Transport;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginApi extends BaseService {

    private Context context;
    final RetrofitRequestBody requestBody;

    public LoginApi(Context context){
        this.context = context;
        requestBody = new RetrofitRequestBody();
    }

    public void studentLogin(String studentId, String password, String fcm_token, BooleanResponse booleanResponse){
        Call<ResponseBody> studentLogin = service.studentLogin(studentId, password, fcm_token);
        studentLogin.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if(response.isSuccessful() && response != null){
                    try {
                        Gson gson = new Gson();
                        jsonObject =  new JSONObject(response.body().string());
                        System.out.println(">>>>>>>>>> " + jsonObject);

                        Student student = gson.fromJson(jsonObject.getJSONObject("user").toString(), Student.class);
                        student.setClassName(jsonObject.getJSONObject("user").getJSONObject("stdclass").optString("name"));
                        student.setSectionName(jsonObject.getJSONObject("user").getJSONObject("section").optString("name"));
                        student.save();

                        Transport transport = gson.fromJson(jsonObject.getJSONObject("user").getJSONObject("transport_info").toString(), Transport.class);
                        transport.setStudent(student);
                        transport.save();

                   //     Guardian guardian = gson.fromJson(jsonObject.getJSONObject("user").getJSONArray("guardians").toString(), Guardian.class);
                        JSONArray guardianJsonArray = jsonObject.getJSONObject("user").getJSONArray("guardians");

                        if(guardianJsonArray != null){
                            if(guardianJsonArray.length() != 0){
                                for(int i = 0; i < guardianJsonArray.length(); i++){
                                    Guardian guardian = gson.fromJson(guardianJsonArray.get(i).toString(), Guardian.class);
                                    guardian.setStudent(student);
                                    guardian.save();
                                }
                            }
                        }

                        CustomSharedPref.getInstance(context).setUserId(studentId);

                        booleanResponse.response(jsonObject.optBoolean("issuccessful"), "Login");

                    } catch (Exception e) {
                        e.printStackTrace();

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
                System.out.println(">>>>>>>>>> " + t.getLocalizedMessage());
            }
        });
    }
}
