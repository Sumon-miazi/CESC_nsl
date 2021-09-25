package com.itbeebd.cesc_nsl.api.studentApi;

import android.content.Context;

import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;

public class ResultApi extends BaseService {
    private Context context;
    final RetrofitRequestBody requestBody;

    public ResultApi(Context context){
        this.context = context;
        requestBody = new RetrofitRequestBody();
    }
/*
    public void getResult(String token, ResponseObj responseObj){

        Call<ResponseBody> getResult = service.getRequestPath(token, ApiUrls.RESULT);
        getResult.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if(response.isSuccessful() && response != null){
                    System.out.println(">>>>>>>>>> due " + response.body());
                    try {
                        jsonObject =  new JSONObject(response.body().string());
                        JSONObject data =  jsonObject.getJSONObject("data");

                        System.out.println(">>>>>>>>>> due " + jsonObject);

                        ArrayList<ResultObj> resultObjArrayList = new ArrayList<>();

                        for(int i = 0; i < jsonObject.length(); i++){
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

                        responseObj.data(due, "successful");

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

 */

}
