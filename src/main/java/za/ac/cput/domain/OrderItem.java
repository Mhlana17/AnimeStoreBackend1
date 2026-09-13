package za.ac.cput.domain;
//Vumbhoni Clifford Mnisi
//222929456
//Group 3G
import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    private String orderItemId;

    @Column(name = "item_description")
    private String itemDescription;

    @Column(name = "item_quantity")
    private int itemQuantity;

    @Column(name = "unit_price")
    private double unitPrice;
//
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    protected OrderItem() {}

    private OrderItem(Builder builder) {
        this.orderItemId = builder.orderItemId;
        this.itemDescription = builder.itemDescription;
        this.itemQuantity = builder.itemQuantity;
        this.unitPrice = builder.unitPrice;
    }

    public String getOrderItemId() { return orderItemId; }
    public int getItemQuantity() { return itemQuantity; }
    public double getUnitPrice() { return unitPrice; }
    public String getItemDescription() { return itemDescription; }
    public Order getOrder() { return order; }
//
    public void setOrder(Order order) { this.order = order; }

    @Override
    public String toString() {
        return "\nOrdered Item {" +
                "Item Id ='" + orderItemId + '\'' +
                ", Description ='" + itemDescription + '\'' +
                ", Quantity =" + itemQuantity +
                ", Unit Price =" + unitPrice +
                '}';
    }

    public static class Builder {
        private String orderItemId;
        private String itemDescription;
        private int itemQuantity;
        private double unitPrice;

        public Builder setOrderItemId(String orderItemId) {
            this.orderItemId = orderItemId;
            return this;
        }

        public Builder setItemDescription(String itemDescription) {
            this.itemDescription = itemDescription;
            return this;
        }

        public Builder setItemQuantity(int itemQuantity) {
            this.itemQuantity = itemQuantity;
            return this;
        }

        public Builder setUnitPrice(double unitPrice) {
            this.unitPrice = unitPrice;
            return this;
        }

        public Builder copy(OrderItem orderItem) {
            this.orderItemId = orderItem.getOrderItemId();
            this.itemDescription = orderItem.getItemDescription();
            this.itemQuantity = orderItem.getItemQuantity();
            this.unitPrice = orderItem.getUnitPrice();
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}