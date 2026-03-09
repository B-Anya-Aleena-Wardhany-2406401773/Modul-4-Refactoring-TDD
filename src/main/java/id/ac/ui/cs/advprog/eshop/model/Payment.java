package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Map;

@Builder
@Getter
public class Payment {
    String id;
    String method;
    Map<String, String> paymentData;
    @Setter
    String status;

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.status = "PENDING";

        if(paymentData.isEmpty()){
            throw new IllegalArgumentException();
        } else{
            this.paymentData = paymentData;
        }

        if (PaymentMethod.contains(method)){
            this.method = method;
        } else{
            throw new IllegalArgumentException();
        }
    }

    public Payment(String id, String method, Map<String, String> paymentData, String status) {
        this(id, method, paymentData);

        this.setStatus(status);
    }

    public void setStatus(String status) {
        if (PaymentStatus.contains(status)){
            this.status = status;
        } else{
            throw new IllegalArgumentException();
        }
    }

}
