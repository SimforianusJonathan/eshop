package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;
import java.util.Map;

@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    public Payment(String id, String method, String status, Map<String, String> paymentData) {
        this.id = id;
        this.setStatus(status);
        this.setPaymentMethod(method);
    }

    public void setStatus(String status) {
        if (PaymentStatus.contains(status)) {
            this.status = status;
        }
        else {
            throw new IllegalArgumentException("Invalid payment status: " + status);
        }
    }

    public void setPaymentMethod(String method) {
        if (PaymentMethod.contains(method)) {
            this.method = method;
        }
        else {
            throw new IllegalArgumentException("Invalid payment method: " + method);
        }
    }
}
