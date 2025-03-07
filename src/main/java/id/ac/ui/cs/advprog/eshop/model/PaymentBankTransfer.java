package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import java.util.Map;

public class PaymentBankTransfer extends Payment {
    public PaymentBankTransfer(String id, String method, Map<String, String> paymentData) {
        super(id, method, PaymentStatus.REJECTED.getValue(), paymentData); // default status before validating payment data
        setStatus(setValidateStatus(paymentData));
    }

    public String setValidateStatus(Map<String, String> paymentData) {
        if (paymentData.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (paymentData.get("bankName") == null || paymentData.get("bankName").isEmpty() ||
                paymentData.get("referenceCode") == null || paymentData.get("referenceCode").isEmpty()) {
            return PaymentStatus.REJECTED.getValue();
        }
        return PaymentStatus.SUCCESS.getValue();
    }
}

