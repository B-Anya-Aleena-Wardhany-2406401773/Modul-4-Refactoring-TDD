package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import id.ac.ui.cs.advprog.eshop.service.validator.CashOnDeliveryValidator;
import id.ac.ui.cs.advprog.eshop.service.validator.PaymentValidator;
import id.ac.ui.cs.advprog.eshop.service.validator.VoucherValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    List<Payment> payments;
    List<Order> orders;

    @BeforeEach
    void setUp() {

        List<PaymentValidator> list = List.of(new VoucherValidator(), new CashOnDeliveryValidator());
        paymentService = new PaymentServiceImpl(list);

        ReflectionTestUtils.setField(paymentService, "paymentRepository", paymentRepository);


        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        orders = new ArrayList<>();
        Order order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
        orders.add(order1);

        Order order2 = new Order("7f9e15bb-4b15-42f4-aebc-c3af385fb078",
                products, 1708570000L, "Safira Sudrajat");
        orders.add(order2);

        Order order3 = new Order("f47ac10b-58cc-4372-a567-0e02b2c3d479",
                products, 1708580000L, "Safira Sudrajat");
        orders.add(order3);


        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        payments = new ArrayList<>();

        Payment payment1 = new Payment("7f9e15bb-4b15-42f4-aebc-c3af385fb078",
                "VOUCHER", paymentData);
        payments.add(payment1);

        Payment payment2 = new Payment(orders.get(0).getId(),
                "VOUCHER", paymentData);
        payments.add(payment2);
    }

    @Test
    void testCreatePayment() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.createPayment(orders.get(0), payment.getMethod(), payment.getPaymentData());
        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testCreatePaymentIfAlreadyExists() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        assertNull(paymentService.createPayment(orders.get(0), payment.getMethod(), payment.getPaymentData()));
        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testUpdateStatus() {
        Payment payment = payments.get(1);
        Payment newPayment = new Payment(payment.getId(), payment.getMethod(),
                payment.getPaymentData(), PaymentStatus.SUCCESS.getValue());

        doReturn(payment).when(paymentRepository).findById(payment.getId());
        doReturn(newPayment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.updateStatus(payment.getId(), PaymentStatus.SUCCESS.getValue());

        assertEquals(payment.getId(), result.getId());
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testUpdateStatusInvalidStatus() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        assertThrows(IllegalArgumentException.class,
                () -> paymentService.updateStatus(payment.getId(), "MEOW"));

        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testUpdateStatusInvalidPaymentId() {
        doReturn(null).when(paymentRepository).findById("zczc");

        assertThrows(NoSuchElementException.class,
                () -> paymentService.updateStatus("zczc", PaymentStatus.SUCCESS.getValue()));

        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testFindByIdIfIdFound() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        Payment result = paymentService.findById(payment.getId());
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        doReturn(null).when(paymentRepository).findById("zczc");
        assertNull(paymentService.findById("zczc"));
    }

    @Test
    void testFindAll() {
        doReturn(payments).when(paymentRepository).findAll();

        List<Payment> results = paymentService.findAllPayments();
        assertEquals(2, results.size());
        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    void testCreatePaymentVoucherInvalidCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ABC12345");

        Payment invalidCodePayment = paymentService.createPayment(orders.get(1), PaymentMethod.VOUCHER.getValue(), paymentData);

        assertEquals(invalidCodePayment.getStatus(), PaymentStatus.REJECTED.getValue());
    }

    @Test
    void testCreatePaymentCODNullAddress() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", null);

        Payment nullAddressPayment = paymentService.createPayment(orders.get(2), PaymentMethod.CASH_ON_DELIVERY.getValue(), paymentData);

        assertEquals(nullAddressPayment.getStatus(), PaymentStatus.REJECTED.getValue());
    }
}