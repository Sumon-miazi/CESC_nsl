package com.itbeebd.cesc_nsl.utils;

public class Payment {
    private String accountHead;
    private String month;
    private String amount;
    private String paidAmount;
    private int waiver;
    private int dueAmount;

    public Payment(){ }

    public Payment(String accountHead, String month, String amount, String paidAmount, int waiver, int dueAmount) {
        this.accountHead = accountHead;
        this.month = month;
        this.amount = amount;
        this.paidAmount = paidAmount;
        this.waiver = waiver;
        this.dueAmount = dueAmount;
    }

    public String getAccountHead() {
        return accountHead;
    }

    public String getMonth() {
        return month;
    }

    public String getAmount() {
        return amount;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public int getWaiver() {
        return waiver;
    }

    public int getDueAmount() {
        return dueAmount;
    }
}
