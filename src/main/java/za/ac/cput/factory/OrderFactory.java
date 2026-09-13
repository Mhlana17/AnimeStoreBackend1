package za.ac.cput.factory;
//Vumbhoni Clifford Mnisi
//222929456
//Group 3G
import za.ac.cput.domain.Order;
import za.ac.cput.domain.OrderItem;
import za.ac.cput.util.Helper;

import java.util.List;

public class OrderFactory {

    public static Order createOrder(String orderId,
                                    String orderDate,
                                    Double amount,
                                    String status,
                                    List<OrderItem> items) {


        if (Helper.isNullOrEmpty(orderId) ||
                Helper.isNullOrEmpty(orderDate) ||
                Helper.isNullOrEmpty(status) ||
                !Helper.isValidPrice(amount) ||
                items == null ||
                items.isEmpty()){
            return null;
        }

        return new Order.Builder()
                .setOrderId(orderId)
                .setOrderDate(orderDate)
                .setOrderTotalAmount(amount)
                .setStatus(status)
                .setOrderItems(items)
                .build();
    }
}