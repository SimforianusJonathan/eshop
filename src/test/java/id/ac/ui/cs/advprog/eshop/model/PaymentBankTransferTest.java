package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
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
        PaymentBankTransfer payment = new PaymentBankTransfer("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethod.BANK_TRANSFER.getValue(), this.paymentData);
        payment.setValidateStatus(this.paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testInvalidDataEmptyBankName() {
        this.paymentData.put("bankName", "");
        paymentData.put("referenceCode","42021269");
        PaymentBankTransfer payment = new PaymentBankTransfer("13652556-012a-4c07-b546-54eb1396d79c", PaymentMethod.BANK_TRANSFER.getValue(), this.paymentData);
        payment.setValidateStatus(this.paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testInvalidDataNullBankName() {
        this.paymentData.put("bankName", null);
        paymentData.put("referenceCode","42021269");
        PaymentBankTransfer payment = new PaymentBankTransfer("13652556-012a-4c07-b546-54eb1396d79d", PaymentMethod.BANK_TRANSFER.getValue(), this.paymentData);
        payment.setValidateStatus(this.paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testInvalidDataEmptyReferenceCode() {
        this.paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode","");
        PaymentBankTransfer payment = new PaymentBankTransfer("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethod.BANK_TRANSFER.getValue(), this.paymentData);
        payment.setValidateStatus(this.paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testInvalidNullDeliveryFee() {
        this.paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode",null);
        PaymentBankTransfer payment = new PaymentBankTransfer("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethod.BANK_TRANSFER.getValue(), this.paymentData);
        payment.setValidateStatus(this.paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testValidPaymentData() {
        this.paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode","42021269");
        PaymentBankTransfer payment = new PaymentBankTransfer("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethod.BANK_TRANSFER.getValue(), this.paymentData);
        payment.setValidateStatus(this.paymentData);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }
    
}
