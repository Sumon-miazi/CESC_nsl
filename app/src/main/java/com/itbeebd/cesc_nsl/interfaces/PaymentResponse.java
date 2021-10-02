package com.itbeebd.cesc_nsl.interfaces;

public interface PaymentResponse {
    void response(boolean isSuccess, String voucher_no, String transactionID, String account_id);
}
