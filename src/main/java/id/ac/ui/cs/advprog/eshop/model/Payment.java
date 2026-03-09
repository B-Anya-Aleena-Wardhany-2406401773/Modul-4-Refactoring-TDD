package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
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

        String[] methodList = {"VOUCHER", "CASH_ON_DELIVERY"};
        if (Arrays.stream(methodList).noneMatch(item -> (item.equals(method)))){
            throw new IllegalArgumentException();
        } else{
            this.method = method;
        }
    }

    public Payment(String id, String method, Map<String, String> paymentData, String status) {
        this(id, method, paymentData);

        String[] statusList = {"PENDING", "SUCCESS", "REJECTED"};
        if (Arrays.stream(statusList).noneMatch(item -> (item.equals(status)))){
            throw new IllegalArgumentException();
        } else{
            this.status = status;
        }
    }

    public void setStatus(String status) {
        String[] statusList = {"PENDING", "SUCCESS", "REJECTED"};
        if (Arrays.stream(statusList).noneMatch(item -> (item.equals(status)))){
            throw new IllegalArgumentException();
        } else{
            this.status = status;
        }
    }

}
