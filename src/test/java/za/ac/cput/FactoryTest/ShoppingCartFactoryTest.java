package za.ac.cput.FactoryTest;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.ShoppingCart;
import za.ac.cput.factory.ShoppingCartFactory;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartFactoryTest {
    @Test
    void createShoppingCart_shouldReturnValidCart_whenUserIdValid() {

        ShoppingCart cart = ShoppingCartFactory.createShoppingCart("USER-001");


        assertNotNull(cart);
        assertNotNull(cart.getCartId() );
        assertTrue(cart.getCartId().startsWith("CART-"));
        assertEquals("USER-001", cart.getUserId());
        assertNotNull(cart.getItems());
        assertTrue(cart.getItems().isEmpty());
        assertEquals(0.0, cart.getTotalAmount());
        assertEquals("ACTIVE", cart.getStatus());
    }

    @Test
    void createShoppingCart_shouldThrowException_whenUserIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            ShoppingCartFactory.createShoppingCart(null);
        });
    }

    @Test
    void createShoppingCart_shouldThrowException_whenUserIdIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            ShoppingCartFactory.createShoppingCart("");
        });
    }

    @Test
    void createShoppingCart_shouldThrowException_whenUserIdHasOnlySpaces() {
        assertThrows(IllegalArgumentException.class, () -> {
            ShoppingCartFactory.createShoppingCart("   ");
        });
    }

    @Test
    void createShoppingCart_shouldGenerateUniqueCartIds() {

        ShoppingCart cart1 = ShoppingCartFactory.createShoppingCart("USER-001");
        ShoppingCart cart2 = ShoppingCartFactory.createShoppingCart("USER-002");
        ShoppingCart cart3 = ShoppingCartFactory.createShoppingCart("USER-003");


        assertNotEquals(cart1.getCartId(), cart2.getCartId());
        assertNotEquals(cart1.getCartId(), cart3.getCartId());
        assertNotEquals(cart2.getCartId(), cart3.getCartId());
    }

    @Test
    void createShoppingCart_shouldGenerateIdWithCorrectFormat() {

        ShoppingCart cart = ShoppingCartFactory.createShoppingCart("USER-001");


        assertTrue(cart.getCartId().matches("CART-\\d{8}-\\d{5}"));
    }

    @Test
    void createShoppingCart_shouldStartWithEmptyItemsList() {

        ShoppingCart cart = ShoppingCartFactory.createShoppingCart("USER-001");


        assertNotNull(cart.getItems());
        assertEquals(0, cart.getItems().size());
    }

    @Test
    void createShoppingCart_shouldStartWithZeroTotalAmount() {

        ShoppingCart cart = ShoppingCartFactory.createShoppingCart("USER-001");


        assertEquals(0.0, cart.getTotalAmount());
    }



    @Test
    void createShoppingCartWithStatus_shouldReturnCartWithSpecifiedStatus() {

        ShoppingCart cart = ShoppingCartFactory.createShoppingCartWithStatus("USER-001", "CHECKED_OUT");


        assertNotNull(cart);
        assertNotNull(cart.getCartId());
        assertTrue(cart.getCartId().startsWith("CART-"));
        assertEquals("USER-001", cart.getUserId());
        assertEquals("CHECKED_OUT", cart.getStatus());
        assertTrue(cart.getItems().isEmpty());
        assertEquals(0.0, cart.getTotalAmount());
    }

    @Test
    void createShoppingCartWithStatus_shouldReturnCartWithActiveStatus() {

        ShoppingCart cart = ShoppingCartFactory.createShoppingCartWithStatus("USER-001", "ACTIVE");


        assertEquals("ACTIVE", cart.getStatus());
    }

    @Test
    void createShoppingCartWithStatus_shouldReturnCartWithAbandonedStatus() {

        ShoppingCart cart = ShoppingCartFactory.createShoppingCartWithStatus("USER-001", "ABANDONED");


        assertEquals("ABANDONED", cart.getStatus());
    }

    @Test
    void createShoppingCartWithStatus_shouldThrowException_whenUserIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            ShoppingCartFactory.createShoppingCartWithStatus(null, "ACTIVE");
        });
    }

    @Test
    void createShoppingCartWithStatus_shouldThrowException_whenUserIdIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            ShoppingCartFactory.createShoppingCartWithStatus("", "ACTIVE");
        });
    }

    @Test
    void createShoppingCartWithStatus_shouldThrowException_whenStatusIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            ShoppingCartFactory.createShoppingCartWithStatus("USER-001", null);
        });
    }

    @Test
    void createShoppingCartWithStatus_shouldThrowException_whenStatusIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            ShoppingCartFactory.createShoppingCartWithStatus("USER-001", "");
        });
    }

    @Test
    void createShoppingCartWithStatus_shouldThrowException_whenStatusHasOnlySpaces() {
        assertThrows(IllegalArgumentException.class, () -> {
            ShoppingCartFactory.createShoppingCartWithStatus("USER-001", "   ");
        });
    }

    @Test
    void createShoppingCartWithStatus_shouldStartWithEmptyItemsList() {
        // Act
        ShoppingCart cart = ShoppingCartFactory.createShoppingCartWithStatus("USER-001", "CHECKED_OUT");


        assertNotNull(cart.getItems());
        assertEquals(0, cart.getItems().size());
    }

    @Test
    void createShoppingCartWithStatus_shouldStartWithZeroTotalAmount() {

        ShoppingCart cart = ShoppingCartFactory.createShoppingCartWithStatus("USER-001", "CHECKED_OUT");


        assertEquals(0.0, cart.getTotalAmount());
    }


    @Test
    void updateCartStatus_shouldReturnUpdatedCart() {

        ShoppingCart original = ShoppingCartFactory.createShoppingCart("USER-001");
        String originalCartId = original.getCartId();
        String originalUserId = original.getUserId();


        ShoppingCart updated = ShoppingCartFactory.updateCartStatus(original, "CHECKED_OUT");


        assertEquals("CHECKED_OUT", updated.getStatus());
        assertEquals(originalCartId, updated.getCartId());
        assertEquals(originalUserId, updated.getUserId());
        assertEquals(original.getItems(), updated.getItems());
        assertEquals(original.getTotalAmount(), updated.getTotalAmount());
    }

    @Test
    void updateCartStatus_shouldUpdateToActive() {

        ShoppingCart cart = ShoppingCartFactory.createShoppingCart("USER-001");


        ShoppingCart updated = ShoppingCartFactory.updateCartStatus(cart, "ACTIVE");


        assertEquals("ACTIVE", updated.getStatus());
    }

    @Test
    void updateCartStatus_shouldUpdateToAbandoned() {

        ShoppingCart cart = ShoppingCartFactory.createShoppingCart("USER-001");


        ShoppingCart updated = ShoppingCartFactory.updateCartStatus(cart, "ABANDONED");


        assertEquals("ABANDONED", updated.getStatus());
    }

    @Test
    void updateCartStatus_shouldThrowException_whenCartIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            ShoppingCartFactory.updateCartStatus(null, "CHECKED_OUT");
        });
    }

    @Test
    void updateCartStatus_shouldThrowException_whenStatusIsNull() {

        ShoppingCart cart = ShoppingCartFactory.createShoppingCart("USER-001");


        assertThrows(IllegalArgumentException.class, () -> {
            ShoppingCartFactory.updateCartStatus(cart, null);
        });
    }

    @Test
    void updateCartStatus_shouldThrowException_whenStatusIsEmpty() {

        ShoppingCart cart = ShoppingCartFactory.createShoppingCart("USER-001");


        assertThrows(IllegalArgumentException.class, () -> {
            ShoppingCartFactory.updateCartStatus(cart, "");
        });
    }

    @Test
    void updateCartStatus_shouldThrowException_whenStatusHasOnlySpaces() {
        // Arrange
        ShoppingCart cart = ShoppingCartFactory.createShoppingCart("USER-001");


        assertThrows(IllegalArgumentException.class, () -> {
            ShoppingCartFactory.updateCartStatus(cart, "   ");
        });
    }

    @Test
    void updateCartStatus_shouldPreserveCartId() {

        ShoppingCart original = ShoppingCartFactory.createShoppingCart("USER-001");


        ShoppingCart updated = ShoppingCartFactory.updateCartStatus(original, "CHECKED_OUT");


        assertEquals(original.getCartId(), updated.getCartId());
    }

    @Test
    void updateCartStatus_shouldPreserveUserId() {

        ShoppingCart original = ShoppingCartFactory.createShoppingCart("USER-001");


        ShoppingCart updated = ShoppingCartFactory.updateCartStatus(original, "CHECKED_OUT");


        assertEquals(original.getUserId(), updated.getUserId());
    }

    @Test
    void updateCartStatus_shouldPreserveItems() {

        ShoppingCart original = ShoppingCartFactory.createShoppingCart("USER-001");


        ShoppingCart updated = ShoppingCartFactory.updateCartStatus(original, "CHECKED_OUT");


        assertEquals(original.getItems(), updated.getItems());
    }

    @Test
    void updateCartStatus_shouldPreserveTotalAmount() {

        ShoppingCart original = ShoppingCartFactory.createShoppingCart("USER-001");


        ShoppingCart updated = ShoppingCartFactory.updateCartStatus(original, "CHECKED_OUT");


        assertEquals(original.getTotalAmount(), updated.getTotalAmount());
    }



    @Test
    void getDefaultStatus_shouldReturnACTIVE() {
        assertEquals("ACTIVE", ShoppingCartFactory.getDefaultStatus());
    }

    @Test
    void multipleCartCreations_shouldAllHaveDifferentIds() {

        ShoppingCart cart1 = ShoppingCartFactory.createShoppingCart("USER-001");
        ShoppingCart cart2 = ShoppingCartFactory.createShoppingCart("USER-002");
        ShoppingCart cart3 = ShoppingCartFactory.createShoppingCart("USER-003");
        ShoppingCart cart4 = ShoppingCartFactory.createShoppingCart("USER-004");


        assertNotEquals(cart1.getCartId(), cart2.getCartId());
        assertNotEquals(cart1.getCartId(), cart3.getCartId());
        assertNotEquals(cart1.getCartId(), cart4.getCartId());
        assertNotEquals(cart2.getCartId(), cart3.getCartId());
        assertNotEquals(cart2.getCartId(), cart4.getCartId());
        assertNotEquals(cart3.getCartId(), cart4.getCartId());
    }

    @Test
    void createShoppingCart_shouldGenerateValidIdEachTime() {

        ShoppingCart cart1 = ShoppingCartFactory.createShoppingCart("USER-001");
        ShoppingCart cart2 = ShoppingCartFactory.createShoppingCart("USER-002");


        assertTrue(cart1.getCartId().matches("CART-\\d{8}-\\d{5}"));
        assertTrue(cart2.getCartId().matches("CART-\\d{8}-\\d{5}"));
    }

}
