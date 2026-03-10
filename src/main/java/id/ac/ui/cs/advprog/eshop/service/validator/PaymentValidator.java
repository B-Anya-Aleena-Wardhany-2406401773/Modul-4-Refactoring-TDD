package id.ac.ui.cs.advprog.eshop.service.validator;

import id.ac.ui.cs.advprog.eshop.model.Payment;


public interface PaymentValidator {
    void validate(Payment paymentData);
    String getMethodName();
}
