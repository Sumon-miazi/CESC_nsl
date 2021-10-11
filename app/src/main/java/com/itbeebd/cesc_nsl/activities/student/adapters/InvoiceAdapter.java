package com.itbeebd.cesc_nsl.activities.student.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.student.adapters.viewHolders.InvoiceViewHolder;
import com.itbeebd.cesc_nsl.utils.dummy.Invoice;

public class InvoiceAdapter  extends GenericRecyclerAdapter<Invoice, OnRecyclerObjectClickListener<Invoice>, InvoiceViewHolder> {

    private Context context;

    public InvoiceAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InvoiceViewHolder(inflate(R.layout.single_invoice_info_view, parent), context);
    }

}