package com.itbeebd.cesc_nsl.activities.student.adapters.viewHolders;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.adapters.genericClasses.BaseViewHolder;
import com.itbeebd.cesc_nsl.activities.student.adapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.utils.Payment;

public class PaymentHistoryViewHolder  extends BaseViewHolder<Payment, OnRecyclerObjectClickListener<Payment>> {

    private Dialog dialog;
    private final TextView voucherNoViewId;
    private final TextView paymentMonthId;
    private final TextView paidAmountId;

    private Context context;

    public PaymentHistoryViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;

        voucherNoViewId = itemView.findViewById(R.id.voucherNoViewId);
        paymentMonthId = itemView.findViewById(R.id.paymentMonthId);
        paidAmountId = itemView.findViewById(R.id.paidAmountId);
    }

    @Override
    public void onBind(Payment item,@Nullable OnRecyclerObjectClickListener<Payment> listener) {
        voucherNoViewId.setText(item.getVoucher_no());
        paymentMonthId.setText(item.getDate());
        paidAmountId.setText(item.getAmount());

        voucherNoViewId.setOnClickListener(view -> {
            System.out.println("voucherNoViewId Clicked " + item.getVoucher_no() + " " + listener);

            assert listener != null;
            listener.onItemClicked(item,view);

       //     else {
//                Intent intent = new Intent(context, PaymentInvoiceActivity.class);
//                intent.putExtra("paymentObj",item);
//                context.startActivity(intent);
          //  }
        });
    }

}