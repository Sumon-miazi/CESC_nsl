package com.itbeebd.cesc_nsl.activities.student.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.CheckoutActivity;
import com.itbeebd.cesc_nsl.activities.student.adapters.DuePaymentAdapter;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.api.studentApi.PaymentApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.dao.StudentDao;
import com.itbeebd.cesc_nsl.sugarClass.Student;
import com.itbeebd.cesc_nsl.utils.dummy.Due;
import com.itbeebd.cesc_nsl.utils.dummy.DueHistory;

import java.util.ArrayList;


public class PaymentFragment extends Fragment implements View.OnClickListener {

    private RecyclerView paymentRecyclerView;
    private TextView totolDueAmountHintId;
    private Button checkOutBtn;
    private TextView paymentHistoryBtn;
    private LinearLayout no_Due_history_foundId;
    private ConstraintLayout dueRecordHeaderId;
    private Due due;
    private Student student;

    private String TBL_API_URL;
    private String MERCHANT;
    private String SUCCESS_URL;

    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.student = new StudentDao().getStudent(getContext());
        this.TBL_API_URL = ApiUrls.TBL_API_URL;
        this.MERCHANT = ApiUrls.MERCHANT;
        this.SUCCESS_URL = ApiUrls.SUCCESS_URL;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        totolDueAmountHintId = view.findViewById(R.id.totolDueAmountHintId);
        checkOutBtn = view.findViewById(R.id.checkOutBtn);

        paymentRecyclerView = view.findViewById(R.id.paymentRecyclerViewId);

        dueRecordHeaderId = view.findViewById(R.id.dueRecordHeaderId);
        no_Due_history_foundId = view.findViewById(R.id.no_Due_history_foundId);
    //    paymentHistoryBtn = view.findViewById(R.id.paymentHistoryBtnId);

        checkOutBtn.setOnClickListener(this);
     //   paymentHistoryBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(due == null || CustomSharedPref.getInstance(getContext()).getPayNowClicked()){
            callDueHistoryApi();
        }
        else setUpDueOverview(due);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(CustomSharedPref.getInstance(getContext()).getPayNowClicked()){
            callDueHistoryApi();
        }
    }

    private void callDueHistoryApi() {
        new PaymentApi(getContext()).getDueHistory(
                CustomSharedPref.getInstance(getContext()).getAuthToken(),
                (due, message) -> {
                    CustomSharedPref.getInstance(getContext()).setPayNowClicked(false);
                    try{
                        if(due != null){
                            this.due = due;
                            setUpDueOverview(due);
                        }
                        else{
                            no_Due_history_foundId.setVisibility(View.VISIBLE);
                            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        }
                    }
                    catch (Exception ignore){
                        no_Due_history_foundId.setVisibility(View.VISIBLE);
                    }
                }
        );
    }


    private void setUpDueOverview(Due due){
        totolDueAmountHintId.setText(String.valueOf(due.getTotalDue()));
        System.out.println(" isPayment_on_off >>>>>>>> " + due.isPayment_on_off());
        checkOutBtn.setVisibility(due.isPayment_on_off()? View.VISIBLE : View.GONE);

        if(due.getDueHistoryArrayList() != null) {
            dueRecordHeaderId.setVisibility(View.VISIBLE);
            no_Due_history_foundId.setVisibility(due.getDueHistoryArrayList().size() == 0? View.VISIBLE : View.GONE);
            setDuePaymentAdapter(due.getDueHistoryArrayList());
        }
        else {
            dueRecordHeaderId.setVisibility(View.GONE);
            no_Due_history_foundId.setVisibility(View.VISIBLE);
        }
    }

    private void setDuePaymentAdapter(ArrayList<DueHistory> dueHistories){

        DuePaymentAdapter duePaymentAdapter = new DuePaymentAdapter(getContext());
        duePaymentAdapter.setItems(dueHistories);
        paymentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //    paymentRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        paymentRecyclerView.setAdapter(duePaymentAdapter);
    }

    @Override
    public void onClick(View view) {
//        if(view.getId() == R.id.paymentHistoryBtnId){
//            getActivity().startActivity(new Intent(getActivity(), PaymentHistoryActivity.class));
//        }
//        else
          if(view.getId() == R.id.checkOutBtn){
            new PaymentApi(getContext()).getInvoiceForCheckout(
                    CustomSharedPref.getInstance(getContext()).getAuthToken(),
                    (isSuccess, message, transactionID, account_id) -> {
                        if(isSuccess){
                            System.out.println(" account_id " + account_id);

                            Intent intent = new Intent(getActivity(), CheckoutActivity.class);
                            intent.putExtra("amount", String.valueOf(due.getTotalDue()));
                            intent.putExtra("invoiceNo", message);
                            intent.putExtra("transactionID", transactionID);
                            intent.putExtra("account_id", account_id);
                            getActivity().startActivity(intent);

//                            if(account_id != null){
//                                dbblPayment(account_id);
//                            }
//                            if(transactionID != null){
//                                tblPayment(transactionID);
//                            }
//                            else {
//                                Toast.makeText(getContext(), "Transaction id is null.", Toast.LENGTH_SHORT).show();
//                            }

//                            Intent intent = new Intent(Intent.ACTION_VIEW);
//                            intent.setData(Uri.parse(ApiUrls.INVOICE_URL + message));
//                            getActivity().startActivity(intent);

                            //this code is previous working code
//                            Intent intent = new Intent(getActivity(), DuePaymentCheckOutActivity.class);
//                            intent.putExtra("invoiceNo",message);
//                            getActivity().startActivity(intent);
                        }
                        else Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    }
            );
        }
    }

//    private void tblPayment(String transactionID){
//        String uri = Uri.parse(TBL_API_URL)
//                .buildUpon()
//                .appendQueryParameter("OrderID", transactionID)
//                .appendQueryParameter("Amount", String.valueOf(due.getTotalDue()))
//                .appendQueryParameter("FullName", /* StudentId */ String.valueOf(student.getStudentId()))
//                .appendQueryParameter("Email", student.getEmail())
//                .appendQueryParameter("MerchantID", this.MERCHANT )
//                .appendQueryParameter("PaymentSuccessUrl", this.SUCCESS_URL)
//                .build().toString();
//        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//        startActivity(browserIntent);
//    }
//
//    private void dbblPayment(String account_id){
//
//        DBBL dbbl = new DBBL(
//                String.valueOf(due.getTotalDue()),
//                "NEXUS Debit. Card",
//                String.valueOf( student.getStudentId()),
//                account_id
//        );
//
//        new PaymentApi(getContext()).getDbblUrl(
//                CustomSharedPref.getInstance(getContext()).getAuthToken(),
//                dbbl,
//                (isSuccess, message) -> {
//                    if(isSuccess){
//                        if(message != null){
//                            String uri = Uri.parse(message)
//                                    .buildUpon()
//                                    .build().toString();
//                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                            startActivity(browserIntent);
//                        }
//                    }
//                    else  Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
//                }
//        );
//    }
}