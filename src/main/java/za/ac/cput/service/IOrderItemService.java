package za.ac.cput.service;

// Vumbhoni Clifford Mnisi
// 222929456
// Group 3G

import za.ac.cput.domain.OrderItem;

import java.util.List;

public interface IOrderItemService extends IService<OrderItem, String> {

    List<OrderItem> getAllOrderItems();

    List<OrderItem> getOrderItemsByDescription(String description);
}