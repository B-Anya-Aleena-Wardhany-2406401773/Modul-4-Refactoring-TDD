package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment createPayment(Payment payment) {
        return null;
    }

    @Override
    public Payment updateStatus(String paymentId, String status) {
        return null;
    }

    @Override
    public Payment findById(String paymentId) {
        return null;
    }

    @Override
    public List<Payment> findAllPayments() {
        return null;
    }
}