package com.itbeebd.cesc_nsl.api.studentApi;

import android.content.Context;

import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;
import com.itbeebd.cesc_nsl.interfaces.BooleanResponse;
import com.itbeebd.cesc_nsl.utils.CustomProgressDialog;
import com.itbeebd.cesc_nsl.utils.dummy.GuardianDummy;
import com.itbeebd.cesc_nsl.utils.dummy.StudentDummy;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileEditApi extends BaseService {
    private Context context;
    final RetrofitRequestBody requestBody;
    private CustomProgressDialog progressDialog;

    public ProfileEditApi(Context context){
        this.context = context;
        requestBody = new RetrofitRequestBody();
        this.progressDialog =  new CustomProgressDialog(context, "Loading...");
    }

    public void updatedData(
                            String token,
                            StudentDummy studentDummy,
                            GuardianDummy motherDummy,
                            GuardianDummy fatherDummy,
                            String student_image,
                            String mother_image,
                            String father_image,
                            BooleanResponse booleanResponse
    ){
        progressDialog.show();
        Call<ResponseBody> editProfile = service.editStudentProfile(
                token,
                studentDummy,
                fatherDummy,
                motherDummy,
                getImageFile(student_image, "student_image"),
                getImageFile(mother_image, "mother_image"),
                getImageFile(father_image, "father_image")
                );
        editProfile.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONObject jsonObject = null;
                if(response.isSuccessful() && response != null){
                    try {

                        System.out.println(">>>>>>>>>> isSuccessful " + response);
                        System.out.println(">>>>>>>>>> isSuccessful " + response.body());
                        System.out.println(">>>>>>>>>> isSuccessful " + response.message());

                        jsonObject =  new JSONObject(response.body().string());
                        System.out.println(">>>>>>>>>> isSuccessful " + jsonObject);
                        booleanResponse.response(jsonObject.optBoolean("issuccessful"), "Login");

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>> catch " + e.getLocalizedMessage());;
                        booleanResponse.response(false, e.getLocalizedMessage());
                    }
                }
                else {
                    booleanResponse.response(response.isSuccessful(), response.toString());
                    System.out.println(">>>>>>>>>> else " + response.isSuccessful());
                    System.out.println(">>>>>>>>>> else " + response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(">>>>>>>>>> onFailure2 " + t.getLocalizedMessage());
            }
        });
    }

}
