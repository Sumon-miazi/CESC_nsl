package com.itbeebd.cesc_nsl.api.studentApi;

import android.content.Context;

import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;
import com.itbeebd.cesc_nsl.interfaces.ResponseObj;
import com.itbeebd.cesc_nsl.utils.Attendance;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassRoutineApi extends BaseService {

    private Context context;
    final RetrofitRequestBody requestBody;

    public ClassRoutineApi(Context context) {
        this.context = context;
        requestBody = new RetrofitRequestBody();
    }

    public void getResult(String dayName, String token, ResponseObj responseObj){

        Call<ResponseBody> dashboardHeaderInfo = service.getAttendanceByMonthName(token, requestBody.mapBody(dayName));
        dashboardHeaderInfo.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject data = null;
                if(response.isSuccessful() && response != null){

                    try {
                        data =  new JSONObject(response.body().string());
                        Attendance attendance = new Attendance(
                                data.optInt("present"),
                                data.optInt("absend")
                        );

                        responseObj.data(attendance, "successful");

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
