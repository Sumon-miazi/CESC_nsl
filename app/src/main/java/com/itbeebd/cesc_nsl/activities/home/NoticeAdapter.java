package com.itbeebd.cesc_nsl.activities.home;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.utils.dummy.Notice;

public class NoticeAdapter extends GenericRecyclerAdapter<Notice, OnRecyclerObjectClickListener<Notice>, NoticeViewHolder> {

    private Context context;

    public NoticeAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticeViewHolder(inflate(R.layout.single_home_notice_view, parent), context);
    }

}