package com.itbeebd.cesc_nsl.api.studentApi;

import android.content.Context;

import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;
import com.itbeebd.cesc_nsl.interfaces.ResponseObj;
import com.itbeebd.cesc_nsl.utils.Attendance;
import com.itbeebd.cesc_nsl.utils.CustomProgressDialog;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceApi extends BaseService {

    private Context context;
    final RetrofitRequestBody requestBody;
    private CustomProgressDialog progressDialog;

    public AttendanceApi(Context context) {
        this.context = context;
        requestBody = new RetrofitRequestBody();
        this.progressDialog = new CustomProgressDialog(context, "Loading...");
    }

    public void getAttendanceByMonthName(String monthName, String token, ResponseObj responseObj){
        progressDialog.show();
        Call<ResponseBody> getAttendance = service.getAttendanceByMonthName(token, requestBody.mapBody(monthName));
        getAttendance.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONObject data = null;
                if(response.isSuccessful() && response != null){

                    try {
                        data =  new JSONObject(response.body().string());
                        System.out.println("><><><> " + data);

                        Attendance attendanceObj = new Attendance(
                                data.optInt("present"),
                                data.optInt("absend")
                        );

                        responseObj.data(attendanceObj, "successful");

                    }
                    catch (Exception e){
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
                progressDialog.dismiss();
                System.out.println(">>>>>>>>>> " + t.getLocalizedMessage());
                responseObj.data(null, t.getLocalizedMessage());
            }
        });
    }

}
