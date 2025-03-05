package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    private static final List<String> VALID_STATUSES = List.of("SUCCESS", "REJECTED");
    private static final List<String> VALID_METHODS = List.of("Bank_Transfer", "Voucher_Payment");

    public Payment(String id, String method, String status, Map<String, String> paymentData) {
        this.id = id;
        this.setStatus(status);
        this.setPaymentMethod(method);
    }

    public void setStatus(String status) {
        if (!VALID_STATUSES.contains(status)) {
            throw new IllegalArgumentException("Invalid payment status: " + status);
        }
        this.status = status;
    }

    public void setPaymentMethod(String method) {
        if (!VALID_METHODS.contains(method)) {
            throw new IllegalArgumentException("Invalid payment method: " + method);
        }
        this.method = method;
    }
}
