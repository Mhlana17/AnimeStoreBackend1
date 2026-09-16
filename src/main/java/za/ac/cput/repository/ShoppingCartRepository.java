package za.ac.cput.repository;

import za.ac.cput.domain.ShoppingCart;

import java.util.*;

public class ShoppingCartRepository implements IRepository<ShoppingCart, String> {

    private static ShoppingCartRepository instance = null;
    private Map<String, ShoppingCart> cartMap;

    private ShoppingCartRepository() {
        cartMap = new HashMap<>();
    }

    public static ShoppingCartRepository getInstance() {
        if (instance == null) {
            instance = new ShoppingCartRepository();
        }
        return instance;
    }

    @Override
    public ShoppingCart create(ShoppingCart cart) {
        if (cart == null || cart.getCartId() == null) {
            throw new IllegalArgumentException("ShoppingCart cannot be null");
        }
        cartMap.put(cart.getCartId(), cart);
        return cart;
    }

    @Override
    public Optional<ShoppingCart> read(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        return Optional.ofNullable(cartMap.get(id));
    }

    @Override
    public ShoppingCart update(ShoppingCart cart) {
        if (cart == null || cart.getCartId() == null) {
            throw new IllegalArgumentException("ShoppingCart cannot be null");
        }
        if (!cartMap.containsKey(cart.getCartId())) {
            throw new IllegalStateException("ShoppingCart not found");
        }
        cartMap.put(cart.getCartId(), cart);
        return cart;
    }

    @Override
    public boolean delete(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        return cartMap.remove(id) != null;
    }

    @Override
    public List<ShoppingCart> getAll() {
        return new ArrayList<>(cartMap.values());
    }

    public void clear() {
        cartMap.clear();
    }
}
