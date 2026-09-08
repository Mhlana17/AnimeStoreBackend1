package za.ac.cput.repository;

import za.ac.cput.domain.CartItem;

import java.util.*;

public class CartItemRepository implements IRepository<CartItem, String> {

    private static CartItemRepository instance = null;
    private Map<String, CartItem> cartItemMap;

    private CartItemRepository() {
        cartItemMap = new HashMap<>();
    }

    public static CartItemRepository getInstance() {
        if (instance == null) {
            instance = new CartItemRepository();
        }
        return instance;
    }

    @Override
    public CartItem create(CartItem cartItem) {
        if (cartItem == null || cartItem.getCartItemId() == null) {
            throw new IllegalArgumentException("CartItem cannot be null");
        }
        cartItemMap.put(cartItem.getCartItemId(), cartItem);
        return cartItem;
    }

    @Override
    public Optional<CartItem> read(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        return Optional.ofNullable(cartItemMap.get(id));
    }

    @Override
    public CartItem update(CartItem cartItem) {
        if (cartItem == null || cartItem.getCartItemId() == null) {
            throw new IllegalArgumentException("CartItem cannot be null");
        }
        if (!cartItemMap.containsKey(cartItem.getCartItemId())) {
            throw new IllegalStateException("CartItem not found");
        }
        cartItemMap.put(cartItem.getCartItemId(), cartItem);
        return cartItem;
    }

    @Override
    public boolean delete(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        return cartItemMap.remove(id) != null;
    }

    @Override
    public List<CartItem> getAll() {
        return new ArrayList<>(cartItemMap.values());
    }

    public void clear() {
        cartItemMap.clear();
    }
}
