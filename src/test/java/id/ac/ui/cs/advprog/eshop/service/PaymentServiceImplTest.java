package id.ac.ui.cs.advprog.eshop.service;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {
    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    OrderRepository orderRepository;
    Order order;
    List<Payment> payments;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductID("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        Map<String, String> validVoucherData = new HashMap<>();
        validVoucherData.put("voucherCode", "DISKON2024");
        Map<String, String> invalidVoucherData = new HashMap<>();
        invalidVoucherData.put("voucherCode", "ESHOP1234ABC5678");
        Map<String, String> validBankData = new HashMap<>();
        validBankData.put("bankName","BCA");
        validBankData.put("referenceCode","42069212");
        Map<String, String> invalidBankData = new HashMap<>();
        validBankData.put("bankName","");
        validBankData.put("referenceCode","");

        order = new Order("13652556-012a-4c07-b546-54eb139d679b",
                products, 1708560000L, "Safira Sudrajat");

        payments = new ArrayList<>();
        Payment payment1 = new Payment("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethod.VOUCHER_PAYMENT.getValue(), PaymentStatus.SUCCESS.getValue(), validVoucherData);
        payments.add(payment1);
        Payment payment2 = new Payment("13652556-012a-4c07-b546-54eb1396d79c", PaymentMethod.VOUCHER_PAYMENT.getValue(), PaymentStatus.REJECTED.getValue(), invalidVoucherData);
        payments.add(payment2);
        Payment payment3 = new Payment("13652556-012a-4c07-b546-54eb1396d79d", PaymentMethod.BANK_TRANSFER.getValue(), PaymentStatus.SUCCESS.getValue(), validBankData);
        payments.add(payment3);
        Payment payment4 = new Payment("13652556-012a-4c07-b546-54eb1396d79e", PaymentMethod.BANK_TRANSFER.getValue(), PaymentStatus.REJECTED.getValue(), invalidBankData);
        payments.add(payment4);
    }

    @Test
    void testAddPaymentBankTransfer(){
        Payment currentPayment = payments.get(2);
        doReturn(currentPayment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, currentPayment.getMethod(), currentPayment.getPaymentData());

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(currentPayment.getMethod(), result.getMethod());
        assertEquals(currentPayment.getStatus(), result.getStatus());
    }

    @Test
    void testAddPaymentVoucher() {
        Payment currentPayment = payments.get(0);
        doReturn(currentPayment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, currentPayment.getMethod(), currentPayment.getPaymentData());

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(currentPayment.getMethod(), result.getMethod());
        assertEquals(currentPayment.getStatus(), result.getStatus());
    }

    @Test
    void testSetStatusToSuccessVoucher(){
        Payment payment = payments.get(0);
        doReturn(payment).when(paymentRepository).findById(payment.getId());
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderRepository).findById(payment.getId());
        doReturn(order).when(orderRepository).save(order);

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testSetStatusToRejectedVoucher(){
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).findById(payment.getId());
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderRepository).findById(payment.getId());
        doReturn(order).when(orderRepository).save(order);

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testSetStatusToSuccessBankTransfer(){
        Payment payment = payments.get(3);
        doReturn(payment).when(paymentRepository).findById(payment.getId());
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderRepository).findById(payment.getId());
        doReturn(order).when(orderRepository).save(order);

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testSetStatusToRejectedBankTransfer(){
        Payment payment = payments.get(2);
        doReturn(payment).when(paymentRepository).findById(payment.getId());
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderRepository).findById(payment.getId());
        doReturn(order).when(orderRepository).save(order);

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testsetStatusInvalidStatus() {
        Payment payment = payments.get(0);
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        assertThrows(IllegalArgumentException.class, () -> paymentService.setStatus(payment, "LODONISASI"));

        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testUpdateNonexistentPayment() {
        Payment payment = new Payment("43279278742380", PaymentMethod.VOUCHER_PAYMENT.getValue(), PaymentStatus.REJECTED.getValue(), new HashMap<>());
        doReturn(null).when(paymentRepository).findById("43279278742380");

        assertThrows(NoSuchElementException.class, () -> paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue()));

        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testGetPayment() {
        Payment payment = payments.get(0);
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        Payment result = paymentService.getPayment(payment.getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getMethod(), result.getMethod());
        assertEquals(payment.getStatus(), result.getStatus());
        assertEquals(payment.getPaymentData(), result.getPaymentData());
    }

    @Test
    void testGetPaymentInvalidId() {
        doReturn(null).when(paymentRepository).findById("lodon_id");
        assertNull(paymentService.getPayment("lodon_id"));
    }

    @Test
    void testGetAllPayments() {
        doReturn(payments).when(paymentRepository).findAll();

        List<Payment> paymentsResult = paymentService.getAllPayments();
        assertEquals(payments.size(), paymentsResult.size());
    }

}
