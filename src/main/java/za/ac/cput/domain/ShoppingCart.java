package za.ac.cput.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class ShoppingCart {
    @Id
    private String cartId;
    private String userId;
    @OneToMany(cascade = CascadeType.ALL)
    private List<CartItem> items;
    private double totalAmount;
    private String status;

    protected ShoppingCart() {

    }

    // Private constructor for Builder
    private ShoppingCart(Builder builder) {
        this.cartId = builder.cartId;
        this.userId = builder.userId;
        this.items = builder.items != null ? builder.items : new ArrayList<>();
        this.totalAmount = builder.totalAmount;
        this.status = builder.status;
    }

    public String getCartId()
    {
        return cartId;
    }
    public String getUserId()
    {
        return userId;
    }
    public List<CartItem> getItems()
    {
        return items;
    }
    public double getTotalAmount()
    {
        return totalAmount;
    }
    public String getStatus()
    {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart that = (ShoppingCart) o;
        return Objects.equals(cartId, that.cartId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId);
    }

    @Override
    public String toString() {
        String st1="ShoppingCart: " +"\n"+
                "CartId" + cartId + "\n" +
                "UserId: " + userId + "\n" +
                "Items: " + items +"\n"+
                "Total Amount: " + totalAmount +"\n"+
                "Status: '" + status;
        return st1;
    }


    public static class Builder {
        private String cartId;
        private String userId;
        private List<CartItem> items;
        private double totalAmount;
        private String status;

        public Builder setCartId(String cartId) {
            this.cartId = cartId;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setItems(List<CartItem> items) {
            this.items = items;
            return this;
        }

        public Builder setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }


        public Builder copy(ShoppingCart shoppingCart) {
            this.cartId = shoppingCart.cartId;
            this.userId = shoppingCart.userId;
            this.items = shoppingCart.items != null ? new ArrayList<>(shoppingCart.items) : null;
            this.totalAmount = shoppingCart.totalAmount;
            this.status = shoppingCart.status;
            return this;
        }

        public ShoppingCart build() {
            return new ShoppingCart(this);
        }
    }
}