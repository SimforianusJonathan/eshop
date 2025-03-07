package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

public class PaymentBankTransfer extends Payment{
    public PaymentBankTransfer(String id, String method, String status, Map<String, String> paymentData) {
        super(id, method, status, paymentData);
    }


    public void setValidateStatus(Map<String, String> paymentData) {
    }
}
