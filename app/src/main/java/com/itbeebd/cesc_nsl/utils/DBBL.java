package com.itbeebd.cesc_nsl.utils;

public class DBBL {
    private final String amount;
    private final String cardtype;
    private final String txnrefnum; // student_id
    private final String account_id;

    public DBBL(String amount, String cardtype, String txnrefnum, String account_id) {
        this.amount = amount;
        this.cardtype = cardtype;
        this.txnrefnum = txnrefnum;
        this.account_id = account_id;
    }

    public String getAmount() {
        return amount;
    }

    public String getCardtype() {
        return cardtype;
    }

    public String getTxnrefnum() {
        return txnrefnum;
    }

    public String getAccount_id() {
        return account_id;
    }
}
