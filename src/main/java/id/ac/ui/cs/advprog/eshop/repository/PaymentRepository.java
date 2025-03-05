package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentRepository {
    private List<Payment> paymentData = new ArrayList<>();

    public void save(Payment payment) {
        int i = 0;
        for (Payment savedPayment : paymentData) {
            if (savedPayment.getId().equals(payment.getId())) {
                paymentData.remove(i);
                paymentData.add(i, payment);
                return;
            }
            i += 1;
        }

        paymentData.add(payment);
    }

    public Payment findById(String id) {
        for (Payment savedpayment : paymentData) {
            if (savedpayment.getId().equals(id)) {
                return savedpayment;
            }
        }
        return null;
    }

    public List<Payment> findAll() {
        return new ArrayList<>(paymentData);
    }
}
