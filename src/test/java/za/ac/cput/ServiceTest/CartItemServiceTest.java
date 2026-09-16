package za.ac.cput.ServiceTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.CartItem;
import za.ac.cput.factory.CartItemFactory;
import za.ac.cput.repository.ICartItemRepository;
import za.ac.cput.service.ICartItemService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CartItemServiceTest {

    @Autowired
    private ICartItemService cartItemService;

    @Autowired
    private ICartItemRepository cartItemRepository;

    @Test
    void create_ShouldPersistCartItemToDatabase() {
        CartItem cartItem = CartItemFactory.createCartItem("PROD001", 2, 50.0);

        CartItem created = cartItemService.create(cartItem);

        assertNotNull(created);
        assertEquals(cartItem.getCartItemId(), created.getCartItemId());
        assertTrue(cartItemRepository.findById(cartItem.getCartItemId()).isPresent());
    }

    @Test
    void read_WhenCartItemExists_ShouldReturnIt() {
        CartItem cartItem = CartItemFactory.createCartItem("PROD001", 2, 50.0);
        cartItemRepository.save(cartItem);

        Optional<CartItem> result = cartItemService.read(cartItem.getCartItemId());

        assertTrue(result.isPresent());
        assertEquals(cartItem.getCartItemId(), result.get().getCartItemId());
    }

    @Test
    void read_WhenCartItemDoesNotExist_ShouldReturnEmptyOptional() {
        Optional<CartItem> result = cartItemService.read("NON_EXISTENT_ID");

        assertTrue(result.isEmpty());
    }

    @Test
    void update_WhenCartItemExists_ShouldPersistChangesAndReturnUpdatedCartItem() {
        CartItem cartItem = CartItemFactory.createCartItem("PROD001", 2, 50.0);
        cartItemRepository.save(cartItem);

        CartItem updatedCartItem = CartItemFactory.updateQuantity(cartItem, 5, 50.0);

        CartItem result = cartItemService.update(updatedCartItem);

        assertNotNull(result);
        assertEquals(5, result.getQuantity());
        assertEquals(250.0, result.getLineTotal());

        CartItem fromDb = cartItemRepository.findById(cartItem.getCartItemId()).orElseThrow();
        assertEquals(5, fromDb.getQuantity());
        assertEquals(250.0, fromDb.getLineTotal());
    }

    @Test
    void update_WhenCartItemIsNull_ShouldReturnNull() {
        CartItem result = cartItemService.update(null);

        assertNull(result);
    }

    @Test
    void update_WhenCartItemIdIsNull_ShouldReturnNull() {
        CartItem cartItem = new CartItem.Builder()
                .setProductId("PROD001")
                .setQuantity(1)
                .setLineTotal(10.0)
                .build(); // no cartItemId set

        CartItem result = cartItemService.update(cartItem);

        assertNull(result);
    }

    @Test
    void update_WhenCartItemDoesNotExistInDatabase_ShouldReturnNull() {
        CartItem cartItem = CartItemFactory.buildCartItem("ITEM-NOTSAVED-99999", "PROD999", 1, 10.0);

        CartItem result = cartItemService.update(cartItem);

        assertNull(result);
    }

    @Test
    void delete_WhenCartItemExists_ShouldRemoveItAndReturnTrue() {
        CartItem cartItem = CartItemFactory.createCartItem("PROD001", 2, 50.0);
        cartItemRepository.save(cartItem);

        boolean result = cartItemService.delete(cartItem.getCartItemId());

        assertTrue(result);
        assertTrue(cartItemRepository.findById(cartItem.getCartItemId()).isEmpty());
    }

    @Test
    void delete_WhenCartItemDoesNotExist_ShouldReturnFalse() {
        boolean result = cartItemService.delete("NON_EXISTENT_ID");

        assertFalse(result);
    }

    @Test
    void getAll_ShouldReturnAllPersistedCartItems() {
        CartItem item1 = CartItemFactory.createCartItem("PROD001", 2, 50.0);
        CartItem item2 = CartItemFactory.createCartItem("PROD002", 1, 20.0);
        cartItemRepository.save(item1);
        cartItemRepository.save(item2);

        List<CartItem> result = cartItemService.getAll();

        assertNotNull(result);
        assertTrue(result.stream().anyMatch(c -> c.getCartItemId().equals(item1.getCartItemId())));
        assertTrue(result.stream().anyMatch(c -> c.getCartItemId().equals(item2.getCartItemId())));
    }

    @Test
    void getAll_WhenNoCartItemsExist_ShouldReturnEmptyList() {
        List<CartItem> result = cartItemService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}