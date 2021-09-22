package com.itbeebd.cesc_nsl.api.studentApi;

import android.content.Context;

import com.google.gson.Gson;
import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.interfaces.BooleanResponse;
import com.itbeebd.cesc_nsl.interfaces.DueHistoryResponse;
import com.itbeebd.cesc_nsl.sugarClass.Guardian;
import com.itbeebd.cesc_nsl.sugarClass.Student;
import com.itbeebd.cesc_nsl.sugarClass.Transport;
import com.itbeebd.cesc_nsl.utils.Due;
import com.itbeebd.cesc_nsl.utils.DueHistory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentApi extends BaseService {

    private Context context;
    final RetrofitRequestBody requestBody;

    public PaymentApi(Context context) {
        this.context = context;
        requestBody = new RetrofitRequestBody();
    }

    public void getDueHistory(String token, DueHistoryResponse dueHistoryResponse){

        Call<ResponseBody> dueHistory = service.dueHistory(token);
        dueHistory.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if(response.isSuccessful() && response != null){
                    System.out.println(">>>>>>>>>> due " + response.body());
                    try {
                        jsonObject =  new JSONObject(response.body().string());
                        JSONObject data =  jsonObject.getJSONObject("data");

                        System.out.println(">>>>>>>>>> due " + jsonObject);

                        Due due = new Due(
                                data.optInt("totaldue"),
                                data.optInt("total_fee"),
                                data.optInt("ind_fee"),
                                data.optInt("waiver"),
                                data.optInt("paid_amount"),
                                jsonObject.optString("payment_category"),
                                jsonObject.optString("invoice"));

                        ArrayList<DueHistory> dueHistories = new ArrayList<>();

                        JSONArray dueHistoryArray = data.getJSONArray("details");
                        for(int i = 0; i < dueHistoryArray.length(); i++){
                            JSONObject dueHistoryObj = dueHistoryArray.getJSONObject(i);

                            DueHistory dueHistory = new DueHistory(
                                    dueHistoryObj.optInt("academic_year"),
                                    dueHistoryObj.optInt("account_head_id"),
                                    dueHistoryObj.getJSONObject("account_head").getString("name"),
                                    dueHistoryObj.optInt("amount"),
                                    dueHistoryObj.optInt("paid_amount"),
                                    dueHistoryObj.optInt("due_amount"),
                                    dueHistoryObj.optInt("weiber"),
                                    dueHistoryObj.getInt("collected_month"),
                                    dueHistoryObj.optString("month"),
                                    dueHistoryObj.getJSONObject("student").optString("payment_category"),
                                    dueHistoryObj.getJSONObject("student").getJSONObject("stdclass").optString("type")
                            );

                            dueHistories.add(dueHistory);
                        }

                        due.setDueHistoryArrayList(dueHistories);

                        dueHistoryResponse.data(due, "successful");

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>> due catch " + e.fillInStackTrace());

                        dueHistoryResponse.data(null, e.getLocalizedMessage());
                    }


                }
                else {
                    dueHistoryResponse.data(null, response.message());
                    System.out.println(">>>>>>>>>> " + response.isSuccessful());
                    System.out.println(">>>>>>>>>> " + response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println(">>>>>>>>>> " + t.getLocalizedMessage());
                dueHistoryResponse.data(null, t.getLocalizedMessage());
            }
        });
    }
}