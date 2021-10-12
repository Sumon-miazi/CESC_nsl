package com.itbeebd.cesc_nsl.api;

import android.content.Context;

import com.google.gson.Gson;
import com.itbeebd.cesc_nsl.interfaces.ResponseObj;
import com.itbeebd.cesc_nsl.utils.CustomProgressDialog;
import com.itbeebd.cesc_nsl.utils.dummy.Notice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StarterApi extends BaseService {

    private Context context;
    final RetrofitRequestBody requestBody;
    private CustomProgressDialog progressDialog;

    public StarterApi(Context context, String message){
        this.context = context;
        requestBody = new RetrofitRequestBody();
        this.progressDialog = new CustomProgressDialog(context, message);
    }

    public  void getHomeData(ResponseObj responseObj){
        progressDialog.show();
        Call<ResponseBody> studentCall = service.getHomeData();
        studentCall.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONObject data = null;
                if(response.isSuccessful() && response != null){
                    System.out.println(">>>>>>>>>> " + response.body());
                    try {
                        Gson gson = new Gson();
                        data =  new JSONObject(response.body().string());
                        System.out.println(">>>>>>>>>> " + data);

//                        if(!data.optBoolean("isSuccessful")){
//                            responseObj.data(null, data.optString("message"));
//                            return;
//                        }

                        Map<String, Object> allData = new HashMap<>();
                        allData.put("sliderImage", sliderImage(data.getJSONObject("slider").getJSONArray("slider_content")));
                        allData.put("general_notice", extractNotice(data.getJSONObject("notice_cat").getJSONArray("general")));
                        allData.put("academic_notice", extractNotice(data.getJSONObject("notice_cat").getJSONArray("academic")));
                        allData.put("admission_notice", extractNotice(data.getJSONObject("notice_cat").getJSONArray("admission")));

                        responseObj.data(allData, "data.optString(message)");
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
                System.out.println(">>>>>>>>>> getStudentList " + t.getLocalizedMessage());
                responseObj.data(null, t.getLocalizedMessage());

            }
        });
    }

    private ArrayList<Map<String, String>> sliderImage(JSONArray jsonArray){
        ArrayList<Map<String, String>> images = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++){
            try {
                Map<String, String> item = new HashMap<>();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                item.put("title", jsonObject.optString("title"));
                item.put("url", jsonObject.optString("FullUrl") + jsonObject.optString("image"));

                images.add(item);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return images;
    }

    private ArrayList<Notice> extractNotice(JSONArray jsonArray){
        ArrayList<Notice> data = new ArrayList<>();
        Gson gson = new Gson();
        for(int i = 0; i < jsonArray.length(); i++){
            try {
                Notice notice = gson.fromJson(jsonArray.getJSONObject(i).toString(), Notice.class);
                data.add(notice);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}
