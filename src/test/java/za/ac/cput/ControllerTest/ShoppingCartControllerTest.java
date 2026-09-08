package ac.za.cput.ControllerTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.controller.ShoppingCartController;
import za.ac.cput.domain.ShoppingCart;
import za.ac.cput.factory.ShoppingCartFactory;
import za.ac.cput.repository.IShoppingCartRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // rolls back DB changes after each test method
class ShoppingCartControllerTest {

    @Autowired
    private ShoppingCartController shoppingCartController;

    @Autowired
    private IShoppingCartRepository shoppingCartRepository;

    @Test
    void create_ShouldPersistToDatabaseAndReturnCreatedStatus() {
        ShoppingCart cart = ShoppingCartFactory.createShoppingCart("USER001");

        ResponseEntity<ShoppingCart> response = shoppingCartController.create(cart);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(cart.getCartId(), response.getBody().getCartId());

        assertTrue(shoppingCartRepository.findById(cart.getCartId()).isPresent());
    }

    @Test
    void read_WhenCartExistsInDatabase_ShouldReturnOkAndCart() {
        ShoppingCart cart = ShoppingCartFactory.createShoppingCart("USER001");
        shoppingCartRepository.save(cart);

        ResponseEntity<ShoppingCart> response = shoppingCartController.read(cart.getCartId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart.getCartId(), response.getBody().getCartId());
    }

    @Test
    void read_WhenCartDoesNotExist_ShouldReturnNotFound() {
        ResponseEntity<ShoppingCart> response = shoppingCartController.read("NON_EXISTENT_ID");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void update_WhenCartExistsInDatabase_ShouldPersistChangesAndReturnOk() {
        ShoppingCart cart = ShoppingCartFactory.createShoppingCart("USER001");
        shoppingCartRepository.save(cart);

        ShoppingCart updatedCart = ShoppingCartFactory.updateCartStatus(cart, "CHECKED_OUT");

        ResponseEntity<ShoppingCart> response = shoppingCartController.update(updatedCart);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("CHECKED_OUT", response.getBody().getStatus());

        ShoppingCart fromDb = shoppingCartRepository.findById(cart.getCartId()).orElseThrow();
        assertEquals("CHECKED_OUT", fromDb.getStatus());
    }

    @Test
    void update_WhenCartDoesNotExist_ShouldReturnNotFound() {
        ShoppingCart cart = ShoppingCartFactory.createShoppingCart("USER999");

        ResponseEntity<ShoppingCart> response = shoppingCartController.update(cart);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void delete_WhenCartExistsInDatabase_ShouldRemoveItAndReturnNoContent() {
        ShoppingCart cart = ShoppingCartFactory.createShoppingCart("USER001");
        shoppingCartRepository.save(cart);

        ResponseEntity<Void> response = shoppingCartController.delete(cart.getCartId());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void delete_WhenCartDoesNotExist_ShouldReturnNotFound() {
        ResponseEntity<Void> response = shoppingCartController.delete("NON_EXISTENT_ID");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAll_ShouldReturnAllPersistedCarts() {
        ShoppingCart cart1 = ShoppingCartFactory.createShoppingCart("USER001");
        ShoppingCart cart2 = ShoppingCartFactory.createShoppingCart("USER002");
        shoppingCartRepository.save(cart1);
        shoppingCartRepository.save(cart2);

        ResponseEntity<List<ShoppingCart>> response = shoppingCartController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().stream().anyMatch(c -> c.getCartId().equals(cart1.getCartId())));
        assertTrue(response.getBody().stream().anyMatch(c -> c.getCartId().equals(cart2.getCartId())));
    }

    @Test
    void getByUserId_ShouldReturnOnlyCartsForThatUser() {
        ShoppingCart cart1 = ShoppingCartFactory.createShoppingCart("USER_A");
        ShoppingCart cart2 = ShoppingCartFactory.createShoppingCart("USER_A");
        ShoppingCart cart3 = ShoppingCartFactory.createShoppingCart("USER_B");
        shoppingCartRepository.save(cart1);
        shoppingCartRepository.save(cart2);
        shoppingCartRepository.save(cart3);

        ResponseEntity<List<ShoppingCart>> response = shoppingCartController.getByUserId("USER_A");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().stream().allMatch(c -> c.getUserId().equals("USER_A")));
        assertTrue(response.getBody().size() >= 2);
    }

    @Test
    void getByStatus_ShouldReturnOnlyCartsWithThatStatus() {
        ShoppingCart activeCart = ShoppingCartFactory.createShoppingCart("USER001");
        ShoppingCart checkedOutCart = ShoppingCartFactory.createShoppingCartWithStatus("USER002", "CHECKED_OUT");
        shoppingCartRepository.save(activeCart);
        shoppingCartRepository.save(checkedOutCart);

        ResponseEntity<List<ShoppingCart>> response = shoppingCartController.getByStatus("CHECKED_OUT");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().stream().allMatch(c -> c.getStatus().equals("CHECKED_OUT")));
    }
}