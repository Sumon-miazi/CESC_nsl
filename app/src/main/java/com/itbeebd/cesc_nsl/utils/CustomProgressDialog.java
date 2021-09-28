package com.itbeebd.cesc_nsl.utils;

import android.app.ProgressDialog;
import android.content.Context;


public class CustomProgressDialog {

    private final ProgressDialog nDialog;

    public CustomProgressDialog(Context context,String message) {
        nDialog = new ProgressDialog(context);
        this.nDialog.setIndeterminate(true);
        this.nDialog.setMessage(message);
        this.nDialog.setCanceledOnTouchOutside(false);
        // show it

    }

    public void  show(){
        try {
            this.nDialog.show();
        } catch (Exception ignore) {
        }
    }

    public void dismiss(){
        try {
            this.nDialog.dismiss();
        } catch (Exception ignore) {
        }
    }
}