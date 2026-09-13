package za.ac.cput.factory;
import za.ac.cput.domain.ShoppingCart;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class ShoppingCartFactory {

    private static final String DEFAULT_STATUS = "ACTIVE";
    private static Random random = new Random();

    public static ShoppingCart createShoppingCart(String userId) {

        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }


        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int randomNum = random.nextInt(90000) + 10000;
        String cartId = "CART-" + date + "-" + randomNum;

        return new ShoppingCart.Builder()
                .setCartId(cartId)
                .setUserId(userId)
                .setItems(new ArrayList<>())
                .setTotalAmount(0.0)
                .setStatus(DEFAULT_STATUS)
                .build();
    }

    public static ShoppingCart createShoppingCartWithStatus(String userId, String status) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int randomNum = random.nextInt(90000) + 10000;
        String cartId = "CART-" + date + "-" + randomNum;

        return new ShoppingCart.Builder()
                .setCartId(cartId)
                .setUserId(userId)
                .setItems(new ArrayList<>())
                .setTotalAmount(0.0)
                .setStatus(status)
                .build();
    }

    public static ShoppingCart updateCartStatus(ShoppingCart cart, String newStatus) {
        if (cart == null) {
            throw new IllegalArgumentException("ShoppingCart cannot be null");
        }
        if (newStatus == null || newStatus.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }

        return new ShoppingCart.Builder()
                .copy(cart)
                .setStatus(newStatus)
                .build();
    }

    public static String getDefaultStatus() {
        return DEFAULT_STATUS;
    }
}