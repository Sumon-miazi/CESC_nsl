package com.itbeebd.cesc_nsl.api.studentApi;

import android.content.Context;

import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;
import com.itbeebd.cesc_nsl.interfaces.ResponseObj;
import com.itbeebd.cesc_nsl.utils.ClassRoutine;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceApi extends BaseService {

    private Context context;
    final RetrofitRequestBody requestBody;

    public AttendanceApi(Context context) {
        this.context = context;
        requestBody = new RetrofitRequestBody();
    }

    public void getAttendanceByMonthName(String monthName, String token, ResponseObj responseObj){

        Call<ResponseBody> getAttendance = service.getClassRoutineByDayName(token, requestBody.mapBody(monthName));
        getAttendance.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONArray data = null;
                if(response.isSuccessful() && response != null){

                    try {
                        data =  new JSONArray(response.body().string());

                        ArrayList<ClassRoutine> classRoutineArrayList = new ArrayList<>();
                        for (int i = 0; i < data.length(); i++){
                            JSONObject object = data.getJSONObject(i);

                            ClassRoutine classRoutine = new ClassRoutine(
                                    object.optString("subject"),
                                    object.optString("teacher"),
                                    object.getJSONObject("duration").optString("winter_start") + " " + object.getJSONObject("duration").optString("winter_end"),
                                    object.getJSONObject("duration").optString("start") + " " + object.getJSONObject("duration").optString("end")
                            );

                            classRoutineArrayList.add(classRoutine);
                        }

                        responseObj.data(classRoutineArrayList, "successful");

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>> due catch " + e.fillInStackTrace());

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
                System.out.println(">>>>>>>>>> " + t.getLocalizedMessage());
                responseObj.data(null, t.getLocalizedMessage());
            }
        });
    }

}
