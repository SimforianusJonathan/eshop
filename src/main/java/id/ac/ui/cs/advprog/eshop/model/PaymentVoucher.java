package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

public class PaymentVoucher extends Payment {
    public PaymentVoucher(String id, String method, Map<String, String> paymentData) {
        super(id, method, validateVoucher(paymentData), paymentData);
    }


    private static String validateVoucher(Map<String, String> paymentData) {
        return null;
    }


}