package com.itbeebd.cesc_nsl.api.studentApi;

import android.content.Context;

import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;
import com.itbeebd.cesc_nsl.interfaces.ResponseObj;
import com.itbeebd.cesc_nsl.utils.CustomProgressDialog;
import com.itbeebd.cesc_nsl.utils.dummy.OnlineClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnlineClassApi extends BaseService {
    private Context context;
    final RetrofitRequestBody requestBody;
    private CustomProgressDialog progressDialog;

    public OnlineClassApi(Context context) {
        this.context = context;
        requestBody = new RetrofitRequestBody();
        this.progressDialog = new CustomProgressDialog(context, "Loading...");
    }

    public void getOnlineClass(String token, ResponseObj responseObj){
        progressDialog.show();
        Call<ResponseBody> onlineClassCall = service.getRequestPath(token, ApiUrls.ONLINE_CLASS);
        onlineClassCall.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONArray data;
                if(response.isSuccessful() && response != null){

                    try {
                        data =  new JSONArray(response.body().string());

                        System.out.println(">>>>>>>>>> onlineClassCall " + data);

                        ArrayList<OnlineClass> onlineClasses = new ArrayList<>();
                        for (int i = 0; i < data.length(); i++){
                            JSONObject object = data.getJSONObject(i);
                            System.out.println(">>>>>>>>>> onlineClassObject " + object);
                                OnlineClass onlineClass = new OnlineClass(
                                        object.getJSONObject("subject").optString("name"),
                                        object.optString("title"),
                                        object.getJSONObject("teacher").optString("name"),
                                        object.getJSONObject("teacher").optString("designation"),
                                        object.getJSONObject("teacher").optString("image"),
                                        object.optString("image"),
                                        object.optString("file"),
                                        object.optString("url")
                               );
                            onlineClasses.add(onlineClass);

                        }

                        responseObj.data(onlineClasses, "successful");

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>> classRoutine catch " + e.fillInStackTrace());

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
