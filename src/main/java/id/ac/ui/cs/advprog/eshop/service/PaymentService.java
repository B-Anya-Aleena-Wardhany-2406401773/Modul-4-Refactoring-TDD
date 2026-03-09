package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import java.util.List;
import java.util.Map;

public interface PaymentService {
    public Payment createPayment(Payment payment);
    public Payment updateStatus(String paymentId, String status);
    public Payment findById(String paymentId);
    public List<Payment> findAllPayments();
}