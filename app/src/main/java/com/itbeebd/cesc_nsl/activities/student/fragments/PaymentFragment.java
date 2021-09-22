package com.itbeebd.cesc_nsl.activities.student.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.PaymentHistoryActivity;
import com.itbeebd.cesc_nsl.activities.student.adapters.DuePaymentAdapter;
import com.itbeebd.cesc_nsl.api.studentApi.PaymentApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.interfaces.DueHistoryResponse;
import com.itbeebd.cesc_nsl.utils.Due;
import com.itbeebd.cesc_nsl.utils.DueHistory;
import com.itbeebd.cesc_nsl.utils.Payment;

import java.util.ArrayList;


public class PaymentFragment extends Fragment implements View.OnClickListener {

    private RecyclerView paymentRecyclerView;
    private TextView totolDueAmountHintId;
    private Button checkOutBtn;
    private TextView paymentHistoryBtn;

    public PaymentFragment() {
        // Required empty public constructor
    }

    public static PaymentFragment newInstance(String param1, String param2) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        totolDueAmountHintId = view.findViewById(R.id.totolDueAmountHintId);
        checkOutBtn = view.findViewById(R.id.checkOutBtn);

        paymentRecyclerView = view.findViewById(R.id.paymentRecyclerViewId);

        paymentHistoryBtn = view.findViewById(R.id.paymentHistoryBtnId);

        paymentHistoryBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new PaymentApi(getContext()).getDueHistory(
                CustomSharedPref.getInstance(getContext()).getAuthToken(),
                (due, message) -> {
                    if(due != null){
                        setUpDueOverview(due);
                    }
                    else Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                }
        );
    }



    private void setUpDueOverview(Due due){
        totolDueAmountHintId.setText(String.valueOf(due.getTotalDue()));

        if(due.getDueHistoryArrayList() != null) setDuePaymentAdapter(due.getDueHistoryArrayList());
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
        getActivity().startActivity(new Intent(getActivity(), PaymentHistoryActivity.class));
    }
}