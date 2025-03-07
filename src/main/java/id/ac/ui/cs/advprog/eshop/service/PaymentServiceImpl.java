package id.ac.ui.cs.advprog.eshop.service;
import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import org.springframework.stereotype.Service;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData){
        Payment oldPayment = paymentRepository.findById(order.getId());
        if (oldPayment == null || oldPayment.getStatus().equals(PaymentStatus.REJECTED.getValue())) {
            Payment payment = new Payment(order.getId(), method, PaymentStatus.SUCCESS.getValue(), paymentData);
            paymentRepository.save(payment);
            return payment;
        }
        return null;
    }
    @Override
    public Payment setStatus(Payment payment, String status){
        Payment oldPayment = paymentRepository.findById(payment.getId());
        if (oldPayment == null) {
            throw new NoSuchElementException();
        }
        oldPayment.setStatus(status);
        paymentRepository.save(oldPayment);
        Order order = orderRepository.findById(payment.getId());
        if (order == null) {
            throw new NoSuchElementException();
        }
        order.setStatus(status.equals(PaymentStatus.REJECTED.getValue()) ? OrderStatus.FAILED.getValue() : PaymentStatus.SUCCESS.getValue());
        orderRepository.save(order);
        return oldPayment;
    }

    @Override
    public Payment getPayment(String paymentId){
        return paymentRepository.findById(paymentId);
    }
    @Override
    public List<Payment> getAllPayments(){
        return paymentRepository.findAll();
    }
}