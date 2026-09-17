package za.ac.cput.service;

// Vumbhoni Clifford Mnisi
// 222929456
// Group 3G

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.OrderItem;
import za.ac.cput.repository.IOrderItemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService implements IOrderItemService {

    @Autowired
    private IOrderItemRepository repository;

    @Override
    public OrderItem create(OrderItem orderItem) {
        return repository.save(orderItem);
    }

    @Override
    public Optional<OrderItem> read(String orderItemId) {

        OrderItem item = repository.findByOrderItemId(orderItemId);

        if (item == null) {
            return Optional.empty();
        }

        return Optional.of(item);
    }

    @Override
    public OrderItem update(OrderItem orderItem) {

        if (repository.existsById(orderItem.getOrderItemId())) {
            return repository.save(orderItem);
        }

        return null;
    }

    @Override
    public boolean delete(String id) {

        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public List<OrderItem> getAll() {
        return List.of();
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return repository.findAll();
    }

    @Override
    public List<OrderItem> getOrderItemsByDescription(String description) {
        return repository.findByItemDescription(description);
    }
}