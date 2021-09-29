package com.itbeebd.cesc_nsl.utils.dummy;

import java.util.ArrayList;

public class Due {
    private int totalDue;
    private int totalFee;
    private int indFee;
    private int waiver;
    private int paidAmount;
    private String paymentVCategory;
    private String invoice;
    private ArrayList<DueHistory> dueHistoryArrayList;

    public Due(int totalDue, int totalFee, int indFee, int waiver, int paidAmount, String paymentVCategory, String invoice) {
        this.totalDue = totalDue;
        this.totalFee = totalFee;
        this.indFee = indFee;
        this.waiver = waiver;
        this.paidAmount = paidAmount;
        this.paymentVCategory = paymentVCategory;
        this.invoice = invoice;
    }

    public void setDueHistoryArrayList(ArrayList<DueHistory> dueHistoryArrayList) {
        this.dueHistoryArrayList = dueHistoryArrayList;
    }

    public int getTotalDue() {
        return totalDue;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public int getIndFee() {
        return indFee;
    }

    public int getWaiver() {
        return waiver;
    }

    public int getPaidAmount() {
        return paidAmount;
    }

    public String getPaymentVCategory() {
        return paymentVCategory;
    }

    public String getInvoice() {
        return invoice;
    }

    public ArrayList<DueHistory> getDueHistoryArrayList() {
        return dueHistoryArrayList;
    }
}
