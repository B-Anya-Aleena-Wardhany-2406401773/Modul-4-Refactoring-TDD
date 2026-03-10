package id.ac.ui.cs.advprog.eshop.service.validator;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class VoucherValidator implements PaymentValidator {
    @Override
    public void validate(Payment payment) {
    }
    @Override
    public String getMethodName() {
        return null;
    }
}
