package za.ac.cput.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

//author: Mihlali Mdlangazi
//student number: 222035749
//Date: 23 March 2026

@Entity
public class CartItem {
    @Id
    private String cartItemId;
    private String productId;
    private int quantity;
    private double lineTotal;


    protected CartItem() {
    }

    private CartItem(Builder builder) {
        this.cartItemId = builder.cartItemId;
        this.productId = builder.productId;
        this.quantity = builder.quantity;
        this.lineTotal = builder.lineTotal;
    }
    // Getters
    public String getCartItemId()
    {
        return cartItemId;
    }
    public String getProductId()
    {
        return productId;
    }
    public int getQuantity()
    { return quantity;
    }
    public double getLineTotal()
    {
        return lineTotal;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(cartItemId, cartItem.cartItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartItemId);
    }

    @Override
    public String toString() {
        String Str =  "CartItem: " +
                "CartItemId: " + cartItemId + "\n" +
                "ProductId: " + productId + "\n" +
                "Quantity: " + quantity +"\n"+
                "LineTotal: " + lineTotal;
        return Str;
    }


    public static class Builder {
        private String cartItemId;
        private String productId;
        private int quantity;
        private double lineTotal;

        public Builder setCartItemId(String cartItemId) {
            this.cartItemId = cartItemId;
            return this;
        }

        public Builder setProductId(String productId) {
            this.productId = productId;
            return this;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setLineTotal(double lineTotal) {
            this.lineTotal = lineTotal;
            return this;
        }


        public Builder copy(CartItem cartItem) {
            this.cartItemId = cartItem.cartItemId;
            this.productId = cartItem.productId;
            this.quantity = cartItem.quantity;
            this.lineTotal = cartItem.lineTotal;
            return this;
        }

        public CartItem build() {
            return new CartItem(this);
        }
    }
}