package za.ac.cput.RepositoryTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.ShoppingCart;
import za.ac.cput.factory.ShoppingCartFactory;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartRepositoryTest {

    private ShoppingCartRepository repository;
    private ShoppingCart cart1;
    private ShoppingCart cart2;

    @BeforeEach
    void setUp() {
        repository = ShoppingCartRepository.getInstance();
        repository.clear();
        cart1 = ShoppingCartFactory.createShoppingCart("USER-001");
        cart2 = ShoppingCartFactory.createShoppingCart("USER-002");
    }

    @Test
    void create_shouldAddCart() {
        ShoppingCart created = repository.create(cart1);
        assertNotNull(created);
        assertTrue(repository.read(cart1.getCartId()).isPresent());
    }

    @Test
    void create_shouldThrowException_whenCartIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            repository.create(null);
        });
    }

    @Test
    void read_shouldReturnCart_whenIdExists() {
        repository.create(cart1);
        Optional<ShoppingCart> found = repository.read(cart1.getCartId());
        assertTrue(found.isPresent());
        assertEquals(cart1.getUserId(), found.get().getUserId());
    }

    @Test
    void read_shouldReturnEmpty_whenIdDoesNotExist() {
        Optional<ShoppingCart> found = repository.read("nonexistent-id");
        assertFalse(found.isPresent());
    }

    @Test
    void read_shouldThrowException_whenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            repository.read(null);
        });
    }

    @Test
    void update_shouldUpdateExistingCart() {
        repository.create(cart1);
        ShoppingCart updated = ShoppingCartFactory.updateCartStatus(cart1, "CHECKED_OUT");
        ShoppingCart result = repository.update(updated);
        assertEquals("CHECKED_OUT", result.getStatus());
    }

    @Test
    void update_shouldThrowException_whenCartNotFound() {
        assertThrows(IllegalStateException.class, () -> {
            repository.update(cart1);
        });
    }

    @Test
    void delete_shouldRemoveCart_whenIdExists() {
        repository.create(cart1);
        boolean deleted = repository.delete(cart1.getCartId());
        assertTrue(deleted);
        assertFalse(repository.read(cart1.getCartId()).isPresent());
    }

    @Test
    void delete_shouldReturnFalse_whenIdDoesNotExist() {
        boolean deleted = repository.delete("nonexistent-id");
        assertFalse(deleted);
    }

    @Test
    void delete_shouldThrowException_whenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            repository.delete(null);
        });
    }

    @Test
    void getAll_shouldReturnAllCarts() {
        repository.create(cart1);
        repository.create(cart2);
        List<ShoppingCart> allCarts = repository.getAll();
        assertEquals(2, allCarts.size());
        assertTrue(allCarts.contains(cart1));
        assertTrue(allCarts.contains(cart2));
    }

    @Test
    void getAll_shouldReturnEmptyList_whenNoCarts() {
        List<ShoppingCart> allCarts = repository.getAll();
        assertTrue(allCarts.isEmpty());
    }

    @Test
    void repository_shouldBeSingleton() {
        ShoppingCartRepository instance1 = ShoppingCartRepository.getInstance();
        ShoppingCartRepository instance2 = ShoppingCartRepository.getInstance();
        assertSame(instance1, instance2);
    }
}