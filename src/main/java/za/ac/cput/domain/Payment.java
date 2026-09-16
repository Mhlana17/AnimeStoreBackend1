package za.ac.cput.domain;

// Kyle Marais 222727993

public class Payment {
    private final String paymentId;
    private final String orderId;
    private final PaymentMethod method;
    private final double amount;
    private final PaymentStatus status;

    private Payment(Builder builder) {
        this.paymentId = builder.paymentId;
        this.orderId = builder.orderId;
        this.method = builder.method;
        this.amount = builder.amount;
        this.status = builder.status;
    }

    // Getters
    public String getPaymentId() {
        return paymentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    // Builder class
    public static class Builder {
        private String paymentId;
        private String orderId;
        private PaymentMethod method;
        private double amount;
        private PaymentStatus status;

        //Setters
        public Builder setPaymentId(String paymentId) {
            this.paymentId = paymentId;
            return this;
        }

        public Builder setOrderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder setMethod(PaymentMethod method) {
            this.method = method;
            return this;
        }

        public Builder setAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder setStatus(PaymentStatus status) {
            this.status = status;
            return this;
        }

        public Payment build() {
            return new Payment(this);
        }
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId='" + paymentId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", method=" + method +
                ", amount=" + amount +
                ", status=" + status +
                '}';
    }
}