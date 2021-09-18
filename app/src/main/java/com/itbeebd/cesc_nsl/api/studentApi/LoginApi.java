package com.itbeebd.cesc_nsl.api.studentApi;

import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginApi extends BaseService {

    final RetrofitRequestBody requestBody;

    public LoginApi(){
        requestBody = new RetrofitRequestBody();
    }

    public void studentLogin(String studentId, String password, String fcm_token){
        Call<ResponseBody> studentLogin = service.studentLogin(studentId, password, fcm_token);
        studentLogin.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject =  new JSONObject(response.body().string());
                    System.out.println(">>>>>>>>>> " + jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(">>>>>>>>>> " + response.isSuccessful());
                System.out.println(">>>>>>>>>> " + response);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println(">>>>>>>>>> " + t.getLocalizedMessage());
            }
        });
    }
}
