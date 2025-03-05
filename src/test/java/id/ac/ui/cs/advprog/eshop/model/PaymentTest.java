package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

class PaymentTest {
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentData = new HashMap<>();

    }

    @Test
    void testCreatePaymentInvalidPaymentMethod() {
        paymentData.put("instalment","12 months");
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment(
                    "13652556-012a-4c07-b546-54eb1396d79b",
                    "Instalment",
                    PaymentStatus.SUCCESS.getValue(),
                    paymentData
            );
        });
    }

    @Test
    void testCreatePaymentBankTransactionSuccessStatus() {
        paymentData.put("bankName","BCA");
        paymentData.put("referenceCode","42021269");
        Payment payment = new Payment(
                "13652556-012a-4c07-b546-54eb1396d79b",
                "Bank_Transfer",
                PaymentStatus.SUCCESS.getValue(),
                paymentData
        );
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherSuccessStatus() {
        paymentData.put("voucherCode","ESHOP1234ABC5678");
        Payment payment = new Payment(
                "13652556-012a-4c07-b546-54eb1396d79b",
                "Voucher_Payment",
                PaymentStatus.SUCCESS.getValue(),
                paymentData
        );
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransactionInvalidStatus() {
        paymentData.put("bankName","BCA");
        paymentData.put("referenceCode","42021269");
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment(
                    "13652556-012a-4c07-b546-54eb1396d79b",
                    "Bank_Transfer",
                    "LODON_BESAR",
                    paymentData
            );
        });
    }

    @Test
    void testCreatePaymentInvalidStatus() {
        paymentData.put("voucherCode","ESHOP1234ABC5678");
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment(
                    "13652556-012a-4c07-b546-54eb1396d79b",
                    "Bank_Transfer",
                    "LODON_KECIL",
                    paymentData
            );
        });
    }

    @Test
    void testCreatePaymentBankTransactionEmptyBankName() {
        paymentData.put("bankName","");
        paymentData.put("referenceCode","42021269");
        Payment payment = new Payment(
                "13652556-012a-4c07-b546-54eb1396d79b",
                "Bank_Transfer",
                PaymentStatus.REJECTED.getValue(),
                paymentData
        );
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransactionEmptyReferenceCode() {
        paymentData.put("bankName","BCA");
        paymentData.put("referenceCode","");
        Payment payment = new Payment(
                "13652556-012a-4c07-b546-54eb1396d79b",
                "Bank_Transfer",
                PaymentStatus.REJECTED.getValue(),
                paymentData
        );
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentInvalidVoucherCode() {
        paymentData.put("voucherCode","270805");
        Payment payment = new Payment(
                "13652556-012a-4c07-b546-54eb1396d79b",
                "Voucher_Payment",
                PaymentStatus.REJECTED.getValue(),
                paymentData
        );
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusSuccess() {
        paymentData.put("bankName","");
        paymentData.put("referenceCode","42021269");
        Payment payment = new Payment(
                "13652556-012a-4c07-b546-54eb1396d79b",
                "Bank_Transfer",
                PaymentStatus.REJECTED.getValue(),
                paymentData
        );
        payment.setStatus(PaymentStatus.SUCCESS.getValue());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusRejected() {
        paymentData.put("bankName","BCA");
        paymentData.put("referenceCode","42021269");
        Payment payment = new Payment(
                "13652556-012a-4c07-b546-54eb1396d79b",
                "Bank_Transfer",
                PaymentStatus.SUCCESS.getValue(),
                paymentData
        );
        payment.setStatus(PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusInvalid() {
        paymentData.put("voucherCode","ESHOP1234ABC5678");
        Payment payment = new Payment(
                "13652556-012a-4c07-b546-54eb1396d79b",
                "Voucher_Payment",
                PaymentStatus.SUCCESS.getValue(),
                paymentData
        );
        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("LODONISASI"));
    }
}

