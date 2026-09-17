package za.ac.cput.service;

// Vumbhoni Clifford Mnisi
// 222929456
// Group 3G

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Order;
import za.ac.cput.repository.IOrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderRepository repository;

    @Override
    public Order create(Order order) {
        return repository.save(order);
    }

    @Override
    public Optional<Order> read(String id) {
        return repository.findById(id);
    }

    @Override
    public Order update(Order order) {
        if (repository.existsById(order.getOrderId())) {
            return repository.save(order);
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
    public List<Order> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    @Override
    public List<Order> getOrdersByOrderId(String orderId) {
        return repository.findByOrderId(orderId);
    }

    @Override
    public List<Order> getOrdersByStatus(String status) {
        return repository.findByStatus(status);
    }
}