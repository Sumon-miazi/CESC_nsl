package com.itbeebd.cesc_nsl.activities.student.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.PaymentHistoryActivity;
import com.itbeebd.cesc_nsl.activities.student.adapters.DuePaymentAdapter;
import com.itbeebd.cesc_nsl.utils.Payment;

import java.util.ArrayList;


public class PaymentFragment extends Fragment implements View.OnClickListener {

    private RecyclerView paymentRecyclerView;
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

        paymentRecyclerView = view.findViewById(R.id.paymentRecyclerViewId);
        paymentHistoryBtn = view.findViewById(R.id.paymentHistoryBtnId);

        paymentHistoryBtn.setOnClickListener(this);
        setDuePaymentAdapter();

        return view;
    }

    private void setDuePaymentAdapter(){
        ArrayList<Payment> payments = new ArrayList<>();
        payments.add(new Payment());
        payments.add(new Payment());


        DuePaymentAdapter duePaymentAdapter = new DuePaymentAdapter(getContext());
        duePaymentAdapter.setItems(payments);
        paymentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //    paymentRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        paymentRecyclerView.setAdapter(duePaymentAdapter);
    }

    @Override
    public void onClick(View view) {
        getActivity().startActivity(new Intent(getActivity(), PaymentHistoryActivity.class));
    }
}