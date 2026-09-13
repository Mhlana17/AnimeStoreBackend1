package za.ac.cput.ServiceTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.ShoppingCart;
import za.ac.cput.factory.ShoppingCartFactory;
import za.ac.cput.repository.IShoppingCartRepository;
import za.ac.cput.service.IShoppingCartService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ShoppingCartServiceTest {

    @Autowired
    private IShoppingCartService shoppingCartService;

    @Autowired
    private IShoppingCartRepository shoppingCartRepository;

    @Test
    void create_ShouldPersistShoppingCartToDatabase() {
        ShoppingCart cart = ShoppingCartFactory.createShoppingCart("USER001");

        ShoppingCart created = shoppingCartService.create(cart);

        assertNotNull(created);
        assertEquals(cart.getCartId(), created.getCartId());
        assertTrue(shoppingCartRepository.findById(cart.getCartId()).isPresent());
    }

    @Test
    void read_WhenCartExists_ShouldReturnIt() {
        ShoppingCart cart = ShoppingCartFactory.createShoppingCart("USER001");
        shoppingCartRepository.save(cart);

        Optional<ShoppingCart> result = shoppingCartService.read(cart.getCartId());

        assertTrue(result.isPresent());
        assertEquals(cart.getCartId(), result.get().getCartId());
    }

    @Test
    void read_WhenCartDoesNotExist_ShouldReturnEmptyOptional() {
        Optional<ShoppingCart> result = shoppingCartService.read("NON_EXISTENT_ID");

        assertTrue(result.isEmpty());
    }

    @Test
    void update_WhenCartExists_ShouldPersistChangesAndReturnUpdatedCart() {
        ShoppingCart cart = ShoppingCartFactory.createShoppingCart("USER001");
        shoppingCartRepository.save(cart);

        ShoppingCart updatedCart = ShoppingCartFactory.updateCartStatus(cart, "CHECKED_OUT");

        ShoppingCart result = shoppingCartService.update(updatedCart);

        assertNotNull(result);
        assertEquals("CHECKED_OUT", result.getStatus());

        ShoppingCart fromDb = shoppingCartRepository.findById(cart.getCartId()).orElseThrow();
        assertEquals("CHECKED_OUT", fromDb.getStatus());
    }

    @Test
    void update_WhenCartIsNull_ShouldReturnNull() {
        ShoppingCart result = shoppingCartService.update(null);

        assertNull(result);
    }

    @Test
    void update_WhenCartIdIsNull_ShouldReturnNull() {
        ShoppingCart cart = new ShoppingCart.Builder()
                .setUserId("USER001")
                .setTotalAmount(0.0)
                .setStatus("ACTIVE")
                .build(); // no cartId set

        ShoppingCart result = shoppingCartService.update(cart);

        assertNull(result);
    }

    @Test
    void update_WhenCartDoesNotExistInDatabase_ShouldReturnNull() {
        ShoppingCart cart = ShoppingCartFactory.createShoppingCart("USER999");


        ShoppingCart result = shoppingCartService.update(cart);

        assertNull(result);
    }

    @Test
    void delete_WhenCartExists_ShouldReturnTrue() {
        ShoppingCart cart = ShoppingCartFactory.createShoppingCart("USER001");
        shoppingCartRepository.save(cart);

        boolean result = shoppingCartService.delete(cart.getCartId());

        assertTrue(result);


        assertTrue(shoppingCartRepository.findById(cart.getCartId()).isPresent());
    }

    @Test
    void delete_WhenCartDoesNotExist_ShouldReturnFalse() {
        boolean result = shoppingCartService.delete("NON_EXISTENT_ID");

        assertFalse(result);
    }

    @Test
    void getAll_ShouldReturnAllPersistedCarts() {
        ShoppingCart cart1 = ShoppingCartFactory.createShoppingCart("USER001");
        ShoppingCart cart2 = ShoppingCartFactory.createShoppingCart("USER002");
        shoppingCartRepository.save(cart1);
        shoppingCartRepository.save(cart2);

        List<ShoppingCart> result = shoppingCartService.getAll();

        assertNotNull(result);
        assertTrue(result.stream().anyMatch(c -> c.getCartId().equals(cart1.getCartId())));
        assertTrue(result.stream().anyMatch(c -> c.getCartId().equals(cart2.getCartId())));
    }

    @Test
    void getAll_WhenNoCartsExist_ShouldReturnEmptyList() {
        List<ShoppingCart> result = shoppingCartService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getShoppingCartsByUserId_ShouldReturnOnlyCartsForThatUser() {
        ShoppingCart cart1 = ShoppingCartFactory.createShoppingCart("USER_A");
        ShoppingCart cart2 = ShoppingCartFactory.createShoppingCart("USER_A");
        ShoppingCart cart3 = ShoppingCartFactory.createShoppingCart("USER_B");
        shoppingCartRepository.save(cart1);
        shoppingCartRepository.save(cart2);
        shoppingCartRepository.save(cart3);

        List<ShoppingCart> result = shoppingCartService.getShoppingCartsByUserId("USER_A");

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(c -> c.getUserId().equals("USER_A")));
    }

    @Test
    void getShoppingCartsByUserId_WhenNoMatch_ShouldReturnEmptyList() {
        List<ShoppingCart> result = shoppingCartService.getShoppingCartsByUserId("NO_SUCH_USER");

        assertTrue(result.isEmpty());
    }

    @Test
    void getShoppingCartsByStatus_ShouldReturnOnlyCartsWithThatStatus() {
        ShoppingCart activeCart = ShoppingCartFactory.createShoppingCart("USER001");
        ShoppingCart checkedOutCart = ShoppingCartFactory.createShoppingCartWithStatus("USER002", "CHECKED_OUT");
        shoppingCartRepository.save(activeCart);
        shoppingCartRepository.save(checkedOutCart);

        List<ShoppingCart> result = shoppingCartService.getShoppingCartsByStatus("CHECKED_OUT");

        assertEquals(1, result.size());
        assertEquals("CHECKED_OUT", result.getFirst().getStatus());
    }

    @Test
    void getShoppingCartsByStatus_WhenNoMatch_ShouldReturnEmptyList() {
        List<ShoppingCart> result = shoppingCartService.getShoppingCartsByStatus("NON_EXISTENT_STATUS");

        assertTrue(result.isEmpty());
    }
}