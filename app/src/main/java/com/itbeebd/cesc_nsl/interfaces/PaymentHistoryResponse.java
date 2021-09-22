package com.itbeebd.cesc_nsl.interfaces;

import com.itbeebd.cesc_nsl.utils.Due;
import com.itbeebd.cesc_nsl.utils.Payment;

import java.util.ArrayList;

public interface PaymentHistoryResponse {
    void data(ArrayList<Payment> payments, String message);
}
