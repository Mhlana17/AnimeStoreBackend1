package za.ac.cput.RepositoryTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.CartItem;
import za.ac.cput.factory.CartItemFactory;
import za.ac.cput.repository.CartItemRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CartItemRepositoryTest {

    private CartItemRepository repository;
    private CartItem item1;
    private CartItem item2;

    @BeforeEach
    void setUp() {
        repository = CartItemRepository.getInstance();
        repository.clear();
        item1 = CartItemFactory.createCartItem("PROD-001", 2, 29.99);
        item2 = CartItemFactory.createCartItem("PROD-002", 1, 49.99);
    }

    @Test
    void create_shouldAddCartItem() {
        CartItem created = repository.create(item1);
        assertNotNull(created);
        assertTrue(repository.read(item1.getCartItemId()).isPresent());
    }

    @Test
    void create_shouldThrowException_whenItemIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            repository.create(null);
        });
    }

    @Test
    void read_shouldReturnItem_whenIdExists() {
        repository.create(item1);
        Optional<CartItem> found = repository.read(item1.getCartItemId());
        assertTrue(found.isPresent());
        assertEquals(item1.getProductId(), found.get().getProductId());
    }

    @Test
    void read_shouldReturnEmpty_whenIdDoesNotExist() {
        Optional<CartItem> found = repository.read("nonexistent-id");
        assertFalse(found.isPresent());
    }

    @Test
    void read_shouldThrowException_whenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            repository.read(null);
        });
    }

    @Test
    void update_shouldUpdateExistingItem() {
        repository.create(item1);
        CartItem updated = CartItemFactory.updateQuantity(item1, 5, 29.99);
        CartItem result = repository.update(updated);
        assertEquals(5, result.getQuantity());
        assertEquals(149.95, result.getLineTotal());
    }

    @Test
    void update_shouldThrowException_whenItemNotFound() {
        assertThrows(IllegalStateException.class, () -> {
            repository.update(item1);
        });
    }

    @Test
    void delete_shouldRemoveItem_whenIdExists() {
        repository.create(item1);
        boolean deleted = repository.delete(item1.getCartItemId());
        assertTrue(deleted);
        assertFalse(repository.read(item1.getCartItemId()).isPresent());
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
    void getAll_shouldReturnAllItems() {
        repository.create(item1);
        repository.create(item2);
        List<CartItem> allItems = repository.getAll();
        assertEquals(2, allItems.size());
        assertTrue(allItems.contains(item1));
        assertTrue(allItems.contains(item2));
    }

    @Test
    void getAll_shouldReturnEmptyList_whenNoItems() {
        List<CartItem> allItems = repository.getAll();
        assertTrue(allItems.isEmpty());
    }

    @Test
    void repository_shouldBeSingleton() {
        CartItemRepository instance1 = CartItemRepository.getInstance();
        CartItemRepository instance2 = CartItemRepository.getInstance();
        assertSame(instance1, instance2);
    }
}
