package za.ac.cput.service;
//Vumbhoni Clifford Mnisi
//222929456
//Group 3G
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.OrderItem;
import za.ac.cput.repository.IOrderItemRepository;

import java.util.List;

@Service
public class OrderItemService implements IOrderItemService {

    @Autowired
    private IOrderItemRepository repository;

    @Override
    public OrderItem create(OrderItem orderItem) {

        return repository.save(orderItem);
    }

    @Override
    public Order read(String orderItemId) {
        OrderItem item = (OrderItem) repository.findByOrderItemId(orderItemId);

        if (item == null) {
            return null;
        }

        return item;
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
    public List<OrderItem> getAllOrderItems() {

        return repository.findAll();
    }

    @Override
    public List<OrderItem> getOrderItemsByDescription(String description) {
        return repository.findByItemDescription(description);
    }
}