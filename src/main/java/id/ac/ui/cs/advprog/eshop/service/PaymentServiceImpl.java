package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import id.ac.ui.cs.advprog.eshop.service.validator.CashOnDeliveryValidator;
import id.ac.ui.cs.advprog.eshop.service.validator.PaymentValidator;
import id.ac.ui.cs.advprog.eshop.service.validator.VoucherValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    private final Map<String, PaymentValidator> validators;

    @Autowired
    public PaymentServiceImpl(List<PaymentValidator> validatorList) {
        this.validators = validatorList.stream()
                .collect(Collectors.toMap(
                        PaymentValidator::getMethodName,
                        validator -> validator
                ));
    }

    @Override
    public Payment createPayment(Payment payment) {
        if (paymentRepository.findById(payment.getId()) == null) {
            PaymentValidator validator = validators.get(payment.getMethod());
            validator.validate(payment);

            return paymentRepository.save(payment);
        }
        return null;
    }

    @Override
    public Payment updateStatus(String paymentId, String status) {
        Payment payment = paymentRepository.findById(paymentId);
        if (payment != null) {
            Payment newPayment = new Payment(payment.getId(),
                    payment.getMethod(), payment.getPaymentData(), status);
            paymentRepository.save(newPayment);
            return newPayment;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Payment findById(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }
}