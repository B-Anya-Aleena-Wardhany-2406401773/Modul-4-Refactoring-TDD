package id.ac.ui.cs.advprog.eshop.service.validator;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CashOnDeliveryValidator implements PaymentValidator {
    @Override
    public void validate(Payment payment) {
        Map<String, String> paymentData = payment.getPaymentData();
        if (paymentData.get("address") == null || paymentData.get("deliveryFee") == null) {
            payment.setStatus(PaymentStatus.REJECTED.getValue());
        }
    }
    @Override
    public String getMethodName() {
        return PaymentMethod.CASH_ON_DELIVERY.getValue();
    }
}
