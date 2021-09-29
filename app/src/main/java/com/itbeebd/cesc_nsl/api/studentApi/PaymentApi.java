package com.itbeebd.cesc_nsl.api.studentApi;

import android.content.Context;

import com.google.gson.Gson;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;
import com.itbeebd.cesc_nsl.interfaces.BooleanResponse;
import com.itbeebd.cesc_nsl.interfaces.DueHistoryResponse;
import com.itbeebd.cesc_nsl.interfaces.PaymentHistoryResponse;
import com.itbeebd.cesc_nsl.utils.CustomProgressDialog;
import com.itbeebd.cesc_nsl.utils.dummy.Due;
import com.itbeebd.cesc_nsl.utils.dummy.DueHistory;
import com.itbeebd.cesc_nsl.utils.dummy.Payment;

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
    private CustomProgressDialog progressDialog;

    public PaymentApi(Context context) {
        this.context = context;
        requestBody = new RetrofitRequestBody();
        this.progressDialog = new CustomProgressDialog(context, "Loading...");
    }

    public void getDueHistory(String token, DueHistoryResponse dueHistoryResponse){
        progressDialog.show();
        Call<ResponseBody> dueHistory = service.getRequestPath(token, ApiUrls.DUE_HISTORY);
        dueHistory.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
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
                progressDialog.dismiss();
                System.out.println(">>>>>>>>>> " + t.getLocalizedMessage());
                dueHistoryResponse.data(null, t.getLocalizedMessage());
            }
        });
    }

    public void getPaymentHistory(String token, PaymentHistoryResponse paymentHistoryResponse){
        progressDialog.show();
        Call<ResponseBody> paymentHistory = service.getRequestPath(token, ApiUrls.PAYMENT_HISTORY);
        paymentHistory.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONArray jsonArray = null;
                if(response.isSuccessful() && response != null){
                    System.out.println(">>>>>>>>>> payment " + response.body());
                    try {
                        Gson gson = new Gson();
                        jsonArray =  new JSONArray(response.body().string());
                        System.out.println(">>>>>>>>>> payment " + jsonArray);

                        ArrayList<Payment> payments = new ArrayList<>();

                        for(int i = 0 ; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Payment payment = new Payment();
                            payment.setTransactionID(jsonObject.optString("transactionID"));
                            payment.setVoucher_no(jsonObject.optString("voucher_no"));
                            payment.setDate(jsonObject.optString("date"));
                            payment.setStatus(jsonObject.optString("status"));
                            payment.setPayment_status(jsonObject.optString("payment_status"));
                            payment.setAmount(jsonObject.optString("amount"));

                            payments.add(payment);
                        }

                        paymentHistoryResponse.data(payments, "successful");

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>> payment catch " + e.fillInStackTrace());

                        paymentHistoryResponse.data(null, e.getLocalizedMessage());
                    }


                }
                else {
                    paymentHistoryResponse.data(null, response.message());
                    System.out.println(">>>>>>>>>> payment " + response.isSuccessful());
                    System.out.println(">>>>>>>>>> payment " + response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(">>>>>>>>>> payment " + t.getLocalizedMessage());
                paymentHistoryResponse.data(null, t.getLocalizedMessage());
            }
        });
    }

    public void getInvoiceForCheckout(String token, BooleanResponse booleanResponse){
        progressDialog.show();
        Call<ResponseBody> invoiceForCheckout = service.postRequestPath(token, ApiUrls.ADD_PAYMENT);
        invoiceForCheckout.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful() && response != null){
                    System.out.println(">>>>>>>>>> payment " + response.body());
                    try {

                        JSONObject jsonObject =  new JSONObject(response.body().string());

                        booleanResponse.response(true, jsonObject.optString("voucher_no"));

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>> payment catch " + e.fillInStackTrace());

                        booleanResponse.response(false, e.getLocalizedMessage());
                    }


                }
                else {
                    booleanResponse.response(false,  response.message());
                    System.out.println(">>>>>>>>>> payment " + response.isSuccessful());
                    System.out.println(">>>>>>>>>> payment " + response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(">>>>>>>>>> payment " + t.getLocalizedMessage());
                booleanResponse.response(false,  t.getLocalizedMessage());
            }
        });
    }
}