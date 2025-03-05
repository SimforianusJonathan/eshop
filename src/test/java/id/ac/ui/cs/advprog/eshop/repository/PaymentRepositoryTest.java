package id.ac.ui.cs.advprog.eshop.repository;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    List<Payment> listPaymentData;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        Map<String,String> paymentData1 = new HashMap<>();
        paymentData1.put("bankName","BCA");
        paymentData1.put("referenceCode","42021269");
        Payment payment1 = new Payment("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethod.BANK_TRANSFER.getValue(), PaymentStatus.SUCCESS.getValue(), paymentData1);

        Map<String,String> paymentData2 = new HashMap<>();
        paymentData2.put("voucherCode","ESHOP1234ABC5678");
        Payment payment2 = new Payment("23652556-012a-4c07-b546-54eb1396d79b", PaymentMethod.VOUCHER_PAYMENT.getValue(), PaymentStatus.SUCCESS.getValue(), paymentData2);

        listPaymentData = new ArrayList<>();
        listPaymentData.add(payment1);
        listPaymentData.add(payment2);
    }

    @Test
    void testSaveCreate() {
        Payment payment = listPaymentData.getFirst();
        paymentRepository.save(payment);

        Payment foundPayment = paymentRepository.findById("13652556-012a-4c07-b546-54eb1396d79b");
        assertNotNull(foundPayment);
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", foundPayment.getId());
        assertEquals("BANK_TRANSFER", foundPayment.getMethod());
        assertEquals("SUCCESS", foundPayment.getStatus());
    }

    @Test
    void testSaveUpdate() {
        Payment firstPayment = listPaymentData.get(1);
        Payment updatedPayment = new Payment("23652556-012a-4c07-b546-54eb1396d79b", PaymentMethod.VOUCHER_PAYMENT.getValue(), PaymentStatus.REJECTED.getValue(), firstPayment.getPaymentData());
        paymentRepository.save(updatedPayment);

        Payment findResult = paymentRepository.findById("23652556-012a-4c07-b546-54eb1396d79b");
        assertNotNull(findResult);
        assertEquals("REJECTED", findResult.getStatus());
    }

    @Test
    void testFindByIdIfIdFound() {
        for (Payment order : listPaymentData) {
            paymentRepository.save(order);
        }

        Payment findResult = paymentRepository.findById(listPaymentData.get(1).getId());
        assertEquals(listPaymentData.get(1).getId(), findResult.getId());
        assertEquals(listPaymentData.get(1).getMethod(), findResult.getMethod());
        assertEquals(listPaymentData.get(1).getStatus(), findResult.getStatus());
        assertEquals(listPaymentData.get(1).getPaymentData(), findResult.getPaymentData());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        for (Payment order : listPaymentData) {
            paymentRepository.save(order);
        }

        Payment findResult = paymentRepository.findById("42069212");
        assertNull(findResult);
    }

    @Test
    void testFindAll() {
        for (Payment order : listPaymentData) {
            paymentRepository.save(order);
        }

        List<Payment> paymentList = paymentRepository.findAll();
        assertEquals(2, paymentList.size());
    }

}
