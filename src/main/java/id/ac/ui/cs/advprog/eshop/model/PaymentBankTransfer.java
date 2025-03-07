package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

public class PaymentBankTransfer extends Payment {
    public PaymentBankTransfer(String id, String method, Map<String, String> paymentData) {
        super(id, method, "REJECTED", paymentData); // default status before validating payment data
        setStatus(setValidateStatus(paymentData));
    }

    public String setValidateStatus(Map<String, String> paymentData) {
        if (paymentData == null || paymentData.get("bankName") == null || paymentData.get("bankName").isEmpty() ||
                paymentData.get("referenceCode") == null || paymentData.get("referenceCode").isEmpty()) {
            return "REJECTED";
        }
        return "SUCCESS";
    }
}

