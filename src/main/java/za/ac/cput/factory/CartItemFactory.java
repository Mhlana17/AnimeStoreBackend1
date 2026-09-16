package za.ac.cput.factory;

import za.ac.cput.domain.CartItem;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

//author: Mihlali Mdlangazi
//student number: 222035749
//Date: 26 March 2026

public class CartItemFactory {

    private static Random random = new Random();


    public static CartItem buildCartItem(String cartItemId, String productId, int quantity, double unitPrice) {

        if (cartItemId == null || cartItemId.trim().isEmpty()) {
            return null;
        }
        if (productId == null || productId.trim().isEmpty()) {
            return null;
        }
        if (quantity <= 0) {
            return null;
        }
        if (unitPrice <= 0) {
            return null;
        }

        double lineTotal = quantity * unitPrice;

        return new CartItem.Builder()
                .setCartItemId(cartItemId)
                .setProductId(productId)
                .setQuantity(quantity)
                .setLineTotal(lineTotal)
                .build();
    }


    public static CartItem createCartItem(String productId, int quantity, double unitPrice) {

        if (productId == null || productId.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (unitPrice <= 0) {
            throw new IllegalArgumentException("Unit price must be greater than 0");
        }


        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int randomNum = random.nextInt(90000) + 10000;
        String cartItemId = "ITEM-" + date + "-" + randomNum;

        double lineTotal = quantity * unitPrice;

        return new CartItem.Builder()
                .setCartItemId(cartItemId)
                .setProductId(productId)
                .setQuantity(quantity)
                .setLineTotal(lineTotal)
                .build();
    }


    public static CartItem updateQuantity(CartItem cartItem, int newQuantity, double unitPrice) {
        if (cartItem == null) {
            throw new IllegalArgumentException("CartItem cannot be null");
        }
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("New quantity must be greater than 0");
        }
        if (unitPrice <= 0) {
            throw new IllegalArgumentException("Unit price must be greater than 0");
        }

        double newLineTotal = newQuantity * unitPrice;

        return new CartItem.Builder()
                .copy(cartItem)
                .setQuantity(newQuantity)
                .setLineTotal(newLineTotal)
                .build();
    }
}