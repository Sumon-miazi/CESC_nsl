package com.itbeebd.cesc_nsl.api.studentApi;

import android.content.Context;

import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;
import com.itbeebd.cesc_nsl.interfaces.BooleanResponse;
import com.itbeebd.cesc_nsl.utils.dummy.GuardianDummy;
import com.itbeebd.cesc_nsl.utils.dummy.StudentDummy;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileEditApi extends BaseService {
    private Context context;
    final RetrofitRequestBody requestBody;

    public ProfileEditApi(Context context){
        this.context = context;
        requestBody = new RetrofitRequestBody();
    }

    public void updatedData(Map<String,Object> studentObjectMap,
                            String token,
                            BooleanResponse booleanResponse
    ){
        System.out.println(">>>>>>>>>> map " + studentObjectMap.toString());
        StudentDummy studentDummy = studentObjectMap.get("student") == null? new StudentDummy() : (StudentDummy) studentObjectMap.get("student") ;
        GuardianDummy motherDummy = studentObjectMap.get("Mother") == null? new GuardianDummy() : (GuardianDummy) studentObjectMap.get("Mother") ;
        GuardianDummy fatherDummy = studentObjectMap.get("Father") == null? new GuardianDummy() : (GuardianDummy) studentObjectMap.get("Father") ;

        Call<ResponseBody> editProfile = service.editStudentProfile(token,
                studentObjectMap,
                getImageFile(studentDummy.getImageUrl(), "student_image"),
                getImageFile(motherDummy.getImageUrl(), "mother_image"),
                getImageFile(fatherDummy.getImageUrl(), "father_image"));
        editProfile.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if(response.isSuccessful() && response != null){
                    try {

                        System.out.println(">>>>>>>>>> isSuccessful " + response);
                        System.out.println(">>>>>>>>>> isSuccessful " + response.body().string());
                        System.out.println(">>>>>>>>>> isSuccessful " + response.message());

                        jsonObject =  new JSONObject(response.body().string());
                        System.out.println(">>>>>>>>>> isSuccessful " + jsonObject);
                        booleanResponse.response(jsonObject.optBoolean("issuccessful"), "Login");

                    } catch (Exception e) {
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
                System.out.println(">>>>>>>>>> onFailure2 " + t.getLocalizedMessage());
            }
        });
    }

}
