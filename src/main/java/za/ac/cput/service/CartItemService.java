package za.ac.cput.service;

import org.springframework.stereotype.Service;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.Order;
import za.ac.cput.repository.ICartItemRepository;

import java.util.List;

@Service
public class CartItemService implements ICartItemService {

    private final ICartItemRepository repository;

    public CartItemService(ICartItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public CartItem create(CartItem cartItem) {
        return repository.save(cartItem);
    }

    @Override
    public Order read(String id) {
        return repository.findById(id);
    }

    @Override
    public CartItem update(CartItem cartItem) {
        if (cartItem == null || cartItem.getCartItemId() == null
                || !repository.existsById(cartItem.getCartItemId())) {
            return null;
        }

        return repository.save(cartItem);
    }

    @Override
    public boolean delete(String id) {
        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    public List<CartItem> getAll() {
        return repository.findAll();
    }
}