package com.itbeebd.cesc_nsl.activities.home;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.BaseViewHolder;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.utils.dummy.Notice;
import com.parassidhu.simpledate.SimpleDateKt;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoticeViewHolder extends BaseViewHolder<Notice, OnRecyclerObjectClickListener<Notice>> {

    private final CardView mainCardLayoutId;
    private final ConstraintLayout cardLayoutId;
    private final TextView title;
    private final TextView description;
    private final TextView date;
    private final Context context;

    public NoticeViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;

        mainCardLayoutId = itemView.findViewById(R.id.mainCardLayoutId);
        cardLayoutId = itemView.findViewById(R.id.cardLayoutId);
        title = itemView.findViewById(R.id.notificationTitleId);
        description = itemView.findViewById(R.id.notificationBodyId);
        date = itemView.findViewById(R.id.dateId);
    }

    @Override
    public void onBind(Notice item, @Nullable OnRecyclerObjectClickListener<Notice> listener) {
        title.setText(Html.fromHtml(item.getTitle()));
        description.setText(SimpleDateKt.toDateStandard(getDateFromString(item.getUpdated_at())));

        cardLayoutId.setOnClickListener(view -> {
//            assert listener != null;
//            listener.onItemClicked(item, view);
            showDialog(item);
        });

//        cardLayoutId.setOnClickListener(view -> {
////            assert listener != null;
////            listener.onItemClicked(item, view);
//            showDialog(item);
//        });
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

    private void showDialog(Notice item){
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.notice_board_dialog);

        TextView noticeTitleId = dialog.findViewById(R.id.noticeTitleId);
        TextView noticeBodyId = dialog.findViewById(R.id.noticeBodyId);
        noticeBodyId.setMovementMethod(LinkMovementMethod.getInstance());
        noticeTitleId.setText(Html.fromHtml(item.getTitle()));
        noticeBodyId.setText(Html.fromHtml(item.getDescription()));

        dialog.show();
    }
}