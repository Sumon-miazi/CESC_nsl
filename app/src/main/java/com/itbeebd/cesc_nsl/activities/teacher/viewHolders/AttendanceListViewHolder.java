package com.itbeebd.cesc_nsl.activities.teacher.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.BaseViewHolder;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.utils.dummy.AttendanceList;
import com.parassidhu.simpledate.SimpleDateKt;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AttendanceListViewHolder extends BaseViewHolder<AttendanceList, OnRecyclerObjectClickListener<AttendanceList>> {

    private final Context context;
    private final TextView attendanceListDateId;
    private final TextView remarkViewId;
    private final CardView attendanceCardViewId;


    public AttendanceListViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;

        this.attendanceListDateId = itemView.findViewById(R.id.attendanceListDateId);
        this.remarkViewId = itemView.findViewById(R.id.remarkViewId);
        this.attendanceCardViewId = itemView.findViewById(R.id.attendanceCardViewId);

    }

    @Override
    public void onBind(AttendanceList item, @Nullable OnRecyclerObjectClickListener<AttendanceList> listener) {

        attendanceListDateId.setText(SimpleDateKt.toDateStandard(getDateFromString(item.getDate())));
        if(item.getRemarks() == null) remarkViewId.setVisibility(View.GONE);
        else {
            remarkViewId.setVisibility(View.VISIBLE);
            remarkViewId.setText(String.format("Remarks: %s", item.getRemarks()));
        }

        attendanceCardViewId.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });
    }

    private Date getDateFromString(String dateString){
        System.out.println("????????? " + dateString);
        Date date;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(dateString);
            System.out.println("????????? " + date);
        } catch (Exception ignore) {
            ignore.printStackTrace();
            date = new Date();
        }
        return date;
    }
}
