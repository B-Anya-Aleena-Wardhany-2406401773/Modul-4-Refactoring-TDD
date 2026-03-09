package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PaymentTest {
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<>();
        this.paymentData.put("voucherCode", "ESHOP1234ABC5678");
    }

    @Test
    void testCreatePaymentEmptyData() {
        this.paymentData.clear();
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("13652556-012a-4c07-b546-54eb1396d79b", "VOUCHER", this.paymentData);
        });
    }

    @Test
    void testCreatePaymentDefaultStatus() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                "VOUCHER", this.paymentData);

        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertSame(this.paymentData, payment.getPaymentData());
        assertEquals("PENDING", payment.getStatus());
    }

    @Test
    void testCreatePaymentSuccessStatus() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "VOUCHER", this.paymentData);
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals("PENDING", payment.getStatus());
        assertSame(this.paymentData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("13652556-012a-4c07-b546-54eb1396d79b", "VOUCHER", this.paymentData, "CRYPTO");
        });
    }

    @Test
    void testCreatePaymentInvalidMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("13652556-012a-4c07-b546-54eb1396d79b", "CARD", this.paymentData);
        });
    }

    @Test
    void testSetStatusToRejected() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "VOUCHER", this.paymentData, "SUCCESS");
        payment.setStatus("REJECTED");
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetStatusToInvalidStatus() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "VOUCHER", this.paymentData);
        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("MEOW"));
    }
}
