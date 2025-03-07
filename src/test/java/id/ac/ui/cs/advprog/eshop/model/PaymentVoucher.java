package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentVoucherTest {
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<>();
    }

    @Test
    void testInvalidEmptyPaymentData() {
        assertThrows(IllegalArgumentException.class, () ->
                new PaymentVoucher("13652556-012a-4c07-b546-54eb1396d79b", "VOUCHER_PAYMENT", this.paymentData));
    }

    @Test
    void testInvalidVoucherShorter16Length(){
        this.paymentData.put("voucherCode", "voucher10d0n15");
        PaymentByVoucher payment = new PaymentByVoucher("13652556-012a-4c07-b546-54eb1396d79c", "VOUCHER_PAYMENT", this.paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalidVoucherLonger16Length(){
        this.paymentData.put("voucherCode", "voucher10d0n15451");
        PaymentByVoucher payment = new PaymentByVoucher("13652556-012a-4c07-b546-54eb1396d79d", "VOUCHER_PAYMENT", this.paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalidVoucherCodePrefixEshop(){
        this.paymentData.put("voucherCode", "voucher10d0n1515");
        PaymentByVoucher payment = new PaymentByVoucher("13652556-012a-4c07-b546-54eb1396d79e", "VOUCHER_PAYMENT", this.paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalidVoucherCodeLess8NumericalCharacther(){
        this.paymentData.put("voucherCode", "ESHOP1234567abcd");
        PaymentByVoucher payment = new PaymentByVoucher("13652556-012a-4c07-b546-54eb1396d79f", "VOUCHER_PAYMENT", this.paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalidVoucherCodeMore8NumericalCharacther(){
        this.paymentData.put("voucherCode", "ESHOP123456789ab");
        PaymentByVoucher payment = new PaymentByVoucher("13652556-012a-4c07-b546-54eb1396d79a", "VOUCHER_PAYMENT", this.paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testVoucherCodeValid(){
        this.paymentData.put("voucherCode", "ESHOP12345678abcd");
        PaymentByVoucher payment = new PaymentByVoucher("13652556-012a-4c07-b546-54eb1396d78b", "VOUCHER_PAYMENT", this.paymentData);
        assertEquals("SUCCESS", payment.getStatus());
    }

}
