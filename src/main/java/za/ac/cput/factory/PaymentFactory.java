package za.ac.cput.factory;

import za.ac.cput.domain.Payment;
import za.ac.cput.domain.PaymentMethod;
import za.ac.cput.domain.PaymentStatus;
import za.ac.cput.util.Helper;

public class PaymentFactory {
    public static Payment createPayment(String paymentId, String orderId, PaymentMethod method, double amount, PaymentStatus status) {
        if ((Helper.isNullOrEmpty(paymentId)) || (Helper.isNullOrEmpty(orderId))) {
            return null;
        }
        if (amount <= 0){
            return null;
        }
        if (method == null || status == null){
            return null;
        }


        return new Payment.Builder()
                .setPaymentId(paymentId)
                .setOrderId(orderId)
                .setMethod(method)
                .setAmount(amount)
                .setStatus(status)
                .build();
    }
}
