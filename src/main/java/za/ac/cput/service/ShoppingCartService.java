package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.ShoppingCart;
import za.ac.cput.repository.IShoppingCartRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService implements IShoppingCartService {


    @Autowired
    private IShoppingCartRepository repository;

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        return repository.save(shoppingCart);
    }

    @Override
    public Optional<ShoppingCart> read(String id) {
        return repository.findById(id);
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        if (shoppingCart == null || shoppingCart.getCartId() == null) {
            return null;
        }

        if (repository.findById(shoppingCart.getCartId()).isEmpty()) {
            return null;
        }

        return repository.save(shoppingCart);
    }

    @Override
    public boolean delete(String id) {
        return repository.existsById(id);
    }

    @Override
    public ShoppingCart save(ShoppingCart shoppingcart) {
        return null;
    }

    @Override
    public ShoppingCart findById(String id) {
        return null;
    }

    @Override
    public List<ShoppingCart> getAll() {
        return repository.findAll();
    }

    @Override
    public List<ShoppingCart> getShoppingCartsByUserId(String userId) {
        return repository.findAll().stream()
                .filter(cart -> cart.getUserId().equals(userId))
                .toList();
    }

    @Override
    public List<ShoppingCart> getShoppingCartsByStatus(String status) {
        return repository.findAll().stream()
                .filter(cart -> cart.getStatus().equals(status))
                .toList();
    }
}