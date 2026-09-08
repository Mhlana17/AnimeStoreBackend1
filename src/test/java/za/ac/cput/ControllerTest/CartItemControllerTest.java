package za.ac.cput.ControllerTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.controller.CartItemController;
import za.ac.cput.domain.CartItem;
import za.ac.cput.factory.CartItemFactory;
import za.ac.cput.repository.ICartItemRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CartItemControllerTest {

    @Autowired
    private CartItemController cartItemController;

    @Autowired
    private ICartItemRepository cartItemRepository;

    @Test
    void create_ShouldPersistToDatabaseAndReturnCreatedStatus() {
        CartItem cartItem = CartItemFactory.createCartItem("PROD001", 2, 50.0);

        ResponseEntity<CartItem> response = cartItemController.create(cartItem);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(cartItem.getCartItemId(), response.getBody().getCartItemId());

        assertTrue(cartItemRepository.findById(cartItem.getCartItemId()).isPresent());
    }

    @Test
    void read_WhenCartItemExistsInDatabase_ShouldReturnOkAndCartItem() {
        CartItem cartItem = CartItemFactory.createCartItem("PROD001", 2, 50.0);
        cartItemRepository.save(cartItem);

        ResponseEntity<CartItem> response = cartItemController.read(cartItem.getCartItemId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(cartItem.getCartItemId(), response.getBody().getCartItemId());
    }

    @Test
    void read_WhenCartItemDoesNotExist_ShouldReturnNotFound() {
        ResponseEntity<CartItem> response = cartItemController.read("NON_EXISTENT_ID");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void update_WhenCartItemExistsInDatabase_ShouldPersistChangesAndReturnOk() {
        CartItem cartItem = CartItemFactory.createCartItem("PROD001", 2, 50.0);
        cartItemRepository.save(cartItem);

        CartItem updatedCartItem = CartItemFactory.updateQuantity(cartItem, 5, 50.0);

        ResponseEntity<CartItem> response = cartItemController.update(updatedCartItem);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(5, response.getBody().getQuantity());
        assertEquals(250.0, response.getBody().getLineTotal());

        CartItem fromDb = cartItemRepository.findById(cartItem.getCartItemId()).orElseThrow();
        assertEquals(5, fromDb.getQuantity());
        assertEquals(250.0, fromDb.getLineTotal());
    }

    @Test
    void update_WhenCartItemDoesNotExist_ShouldReturnNotFound() {
        CartItem cartItem = CartItemFactory.buildCartItem("ITEM-NOTSAVED-99999", "PROD999", 1, 10.0);

        ResponseEntity<CartItem> response = cartItemController.update(cartItem);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void delete_WhenCartItemExistsInDatabase_ShouldRemoveItAndReturnNoContent() {
        CartItem cartItem = CartItemFactory.createCartItem("PROD001", 2, 50.0);
        cartItemRepository.save(cartItem);

        ResponseEntity<Void> response = cartItemController.delete(cartItem.getCartItemId());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertTrue(cartItemRepository.findById(cartItem.getCartItemId()).isEmpty());
    }

    @Test
    void delete_WhenCartItemDoesNotExist_ShouldReturnNotFound() {
        ResponseEntity<Void> response = cartItemController.delete("NON_EXISTENT_ID");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAll_ShouldReturnAllPersistedCartItems() {
        CartItem item1 = CartItemFactory.createCartItem("PROD001", 2, 50.0);
        CartItem item2 = CartItemFactory.createCartItem("PROD002", 1, 20.0);
        cartItemRepository.save(item1);
        cartItemRepository.save(item2);

        ResponseEntity<List<CartItem>> response = cartItemController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().stream().anyMatch(c -> c.getCartItemId().equals(item1.getCartItemId())));
        assertTrue(response.getBody().stream().anyMatch(c -> c.getCartItemId().equals(item2.getCartItemId())));
    }

    @Test
    void getAll_WhenNoCartItemsExist_ShouldReturnOkAndEmptyOrUnaffectedList() {
        ResponseEntity<List<CartItem>> response = cartItemController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}