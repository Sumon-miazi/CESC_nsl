package com.itbeebd.cesc_nsl.activities.student.adapters.viewHolders;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.adapters.genericClasses.BaseViewHolder;
import com.itbeebd.cesc_nsl.activities.student.adapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.sugarClass.NotificationObj;

public class StudentNotificationViewHolder extends BaseViewHolder<NotificationObj, OnRecyclerObjectClickListener<NotificationObj>> {
    private final TextView notificationTitle;
    private final TextView notificationBody;
   // private final SeeMoreTextView notificationBody;
    private Context context;

    public StudentNotificationViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        notificationTitle = itemView.findViewById(R.id.notificationTitleId);
        notificationBody = itemView.findViewById(R.id.notificationBodyId);
    }

    @Override
    public void onBind(NotificationObj item, @Nullable OnRecyclerObjectClickListener<NotificationObj> listener) {

   //     notificationBody.setTextMaxLength(80); //default is 250 charachters
    //    notificationBody.expandText(false);

     //   notificationTitle.setText(item.getTitle());
    //    notificationBody.setContent(String.valueOf(Html.fromHtml(item.getBody())));

        notificationTitle.setText(item.getTitle());

        Spanned policy = Html.fromHtml(item.getBody());
        notificationBody.setText(policy);
        notificationBody.setMovementMethod(LinkMovementMethod.getInstance());

       // notificationBody.toggle();
      //  notificationBody.setSeeMoreTextColor(R.color.blue_primary); //default is #3F51B5
    }
}