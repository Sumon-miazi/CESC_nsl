package com.itbeebd.cesc_nsl.api.studentApi;

import android.content.Context;

import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;
import com.itbeebd.cesc_nsl.interfaces.ResponseObj;
import com.itbeebd.cesc_nsl.utils.dummy.ClassRoutine;
import com.itbeebd.cesc_nsl.utils.CustomProgressDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassRoutineApi extends BaseService {

    private Context context;
    final RetrofitRequestBody requestBody;
    private CustomProgressDialog progressDialog;

    public ClassRoutineApi(Context context) {
        this.context = context;
        requestBody = new RetrofitRequestBody();
        this.progressDialog = new CustomProgressDialog(context, "Loading...");
    }

    public void getClassRoutine(String dayName, String token, ResponseObj responseObj){
        progressDialog.show();
        Call<ResponseBody> dashboardHeaderInfo = service.getClassRoutineByDayName(token, requestBody.mapBody(dayName));
        dashboardHeaderInfo.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONArray data;
                if(response.isSuccessful() && response != null){

                    try {
                        data =  new JSONArray(response.body().string());

                        System.out.println(">>>>>>>>>> classRoutine " + dayName + " " + data);

                        ArrayList<ClassRoutine> classRoutineArrayList = new ArrayList<>();
                        for (int i = 0; i < data.length(); i++){
                            JSONObject object = data.getJSONObject(i);
                            JSONObject durationObj = new JSONObject(object.optString("duration"));

                            System.out.println(">>>>>>>>>> durationObj " + durationObj);


                            if(object.optInt("status") == 1){
                                ClassRoutine classRoutine = new ClassRoutine(
                                        object.optString("subject"),
                                        object.optString("teacher"),
                                        durationObj.optString("winter_start") + " " + durationObj.optString("winter_end"),
                                        durationObj.optString("start") + " " + durationObj.optString("end")
                                        //  object.getJSONObject("duration").optString("winter_start") + " " + object.getJSONObject("duration").optString("winter_end"),
                                        //  object.getJSONObject("duration").optString("start") + " " + object.getJSONObject("duration").optString("end")
                                );
                                classRoutineArrayList.add(classRoutine);
                            }
                        }

                        responseObj.data(classRoutineArrayList, "successful");

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
