package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentBankTransferTest {
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<>();
    }

    @Test
    void testInvalidEmptyPaymentData() {
        PaymentBankTransfer payment = new PaymentBankTransfer("13652556-012a-4c07-b546-54eb1396d79b", "BANK_TRANSFER", this.paymentData);
        payment.setValidateStatus(this.paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalidDataEmptyBankName() {
        this.paymentData.put("bankName", "");
        paymentData.put("referenceCode","42021269");
        PaymentBankTransfer payment = new PaymentBankTransfer("13652556-012a-4c07-b546-54eb1396d79c", "BANK_TRANSFER", this.paymentData);
        payment.setValidateStatus(this.paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalidDataNullBankName() {
        this.paymentData.put("bankName", null);
        paymentData.put("referenceCode","42021269");
        PaymentBankTransfer payment = new PaymentBankTransfer("13652556-012a-4c07-b546-54eb1396d79d", "BANK_TRANSFER", this.paymentData);
        payment.setValidateStatus(this.paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalidDataEmptyReferenceCode() {
        this.paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode","");
        PaymentBankTransfer payment = new PaymentBankTransfer("13652556-012a-4c07-b546-54eb1396d79b", "BANK_TRANSFER", this.paymentData);
        payment.setValidateStatus(this.paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalidNullDeliveryFee() {
        this.paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode",null);
        PaymentBankTransfer payment = new PaymentBankTransfer("13652556-012a-4c07-b546-54eb1396d79b", "BANK_TRANSFER", this.paymentData);
        payment.setValidateStatus(this.paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testValidPaymentData() {
        this.paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode","42021269");
        PaymentBankTransfer payment = new PaymentBankTransfer("13652556-012a-4c07-b546-54eb1396d79b", "BANK_TRANSFER", this.paymentData);
        payment.setValidateStatus(this.paymentData);
        assertEquals("SUCCESS", payment.getStatus());
    }
    
}
