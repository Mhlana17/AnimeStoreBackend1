package za.ac.cput.service;
//Vumbhoni Clifford Mnisi
//222929456
//Group 3G
import za.ac.cput.domain.Order;

import java.util.List;

public interface IOrderService extends IService<Order, String>{
//    Order create(Order order);
//    Order read(String id);
//    Order update(Order order);
//    boolean delete(String id);

    List<Order> getAllOrders();
    List<Order> getOrdersByOrderId(String orderId);
    List<Order> getOrdersByStatus(String status);  // ← Add this if needed
}