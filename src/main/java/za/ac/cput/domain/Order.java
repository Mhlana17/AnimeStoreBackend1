package za.ac.cput.domain;
//Vumbhoni Clifford Mnisi
//222929456
//Group 3G
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    private String orderId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "order_date")
    private String orderDate;

    @Column(name = "total_amount")
    private double orderTotalAmount;

    @Column(name = "status")
    private String status;

    @Column(name = "shipping_address", length = 500)
    private String shippingAddress;

    @OneToMany(mappedBy = "order",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    @JsonIgnore
    private List<OrderItem> orderItems;

    protected Order() {}

    public Order(Builder builder) {
        this.orderId = builder.orderId;
        this.userId = builder.userId;
        this.orderDate = builder.orderDate;
        this.orderTotalAmount = builder.orderTotalAmount;
        this.status = builder.status;
        this.shippingAddress = builder.shippingAddress;
        this.orderItems = builder.orderItems;
        if (this.orderItems != null) {
            for (OrderItem item : this.orderItems) {
                item.setOrder(this);
            }
        }
    }

    // Getters
    public String getOrderId() { return orderId; }
    public String getUserId() { return userId; }
    public String getOrderDate() { return orderDate; }
    public double getOrderTotalAmount() { return orderTotalAmount; }
    public String getStatus() { return status; }
    public String getShippingAddress() { return shippingAddress; }
    public List<OrderItem> getOrderItems() { return orderItems; }

    // Setters
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }
    public void setOrderTotalAmount(double orderTotalAmount) { this.orderTotalAmount = orderTotalAmount; }
    public void setStatus(String status) { this.status = status; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
        if (orderItems != null) {
            for (OrderItem item : orderItems) {
                item.setOrder(this);
            }
        }
    }

    public static class Builder {
        private String orderId;
        private String userId;
        private String orderDate;
        private double orderTotalAmount;
        private String status;
        private String shippingAddress;
        private List<OrderItem> orderItems;

        public Builder setOrderId(String orderId) { this.orderId = orderId; return this; }
        public Builder setUserId(String userId) { this.userId = userId; return this; }
        public Builder setOrderDate(String orderDate) { this.orderDate = orderDate; return this; }
        public Builder setOrderTotalAmount(double orderTotalAmount) { this.orderTotalAmount = orderTotalAmount; return this; }
        public Builder setStatus(String status) { this.status = status; return this; }
        public Builder setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; return this; }
        public Builder setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; return this; }

        public Builder copy(Order order) {
            this.orderId = order.orderId;
            this.userId = order.userId;
            this.orderDate = order.orderDate;
            this.orderTotalAmount = order.orderTotalAmount;
            this.status = order.status;
            this.shippingAddress = order.shippingAddress;
            this.orderItems = order.orderItems;
            return this;
        }

        public Order build() { return new Order(this); }
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderTotalAmount=" + orderTotalAmount +
                ", status='" + status + '\'' +
                ", orderItemsCount=" + (orderItems != null ? orderItems.size() : 0) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId);
    }

    @Override
    public int hashCode() { return Objects.hash(orderId); }
}