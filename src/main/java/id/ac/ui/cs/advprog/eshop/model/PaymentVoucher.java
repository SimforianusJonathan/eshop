package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

public class PaymentVoucher extends Payment {
    public PaymentVoucher(String id, String method, Map<String, String> paymentData) {
        super(id, method, "REJECTED", paymentData);
        setStatus(validateVoucher(paymentData));
    }

    private static String validateVoucher(Map<String, String> paymentData) {
        if (paymentData == null || !paymentData.containsKey("voucherCode")) {
            throw new IllegalArgumentException("Voucher code does not exist");
        }

        String voucherCode = paymentData.get("voucherCode");
        if (voucherCode == null || voucherCode.length() != 16 || !voucherCode.startsWith("ESHOP")) {
            return "REJECTED";
        }

        long digitCount = voucherCode.chars().filter(Character::isDigit).count();
        return (digitCount == 8) ? "SUCCESS" : "REJECTED";
    }
}