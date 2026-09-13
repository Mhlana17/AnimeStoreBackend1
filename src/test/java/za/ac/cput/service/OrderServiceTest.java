package za.ac.cput.service;
//Vumbhoni Clifford Mnisi
//222929456
//Group 3G
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.OrderItem;
import za.ac.cput.factory.OrderFactory;
import za.ac.cput.factory.OrderItemFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderServiceTest {

    @Autowired
    private IOrderService iOrderService;

    private List<OrderItem> orderedItemList;
    private List<OrderItem> invalidList;
    private Order order;
    private Order order1;

    @BeforeEach
    void setUp() {
        orderedItemList = new ArrayList<>();
        invalidList = new ArrayList<>();

        OrderItem orderItem1 = OrderItemFactory.createOrderItem("1010d", "Grey Pants", 2, 100.00);
        orderedItemList.add(orderItem1);

        order = OrderFactory.createOrder("2255a", "12/08/2026",
                200.00, "PENDING", orderedItemList);

        OrderItem orderItem2 = OrderItemFactory.createOrderItem("1011s", "White Shirt", 1, 50.00);
        invalidList.add(orderItem2);

        order1 = OrderFactory.createOrder("2266a", "15/08/2026",
                50.00, "SHIPPED", invalidList);
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void create() {
        Order created = iOrderService.create(order);
        assertNotNull(created);
        assertEquals("2255a", created.getOrderId());
        System.out.println("Order created: " + created);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void read() {
        iOrderService.create(order);
        Order found = iOrderService.read("2255a");
        assertNotNull(found);
        System.out.println("Order found: " + found);
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void update() {
        iOrderService.create(order);
        Order updatedOrder = new Order.Builder()
                .copy(order)
                .setStatus("COMPLETED")
                .build();
        Order updated = iOrderService.update(updatedOrder);
        assertNotNull(updated);
        assertEquals("COMPLETED", updated.getStatus());
        System.out.println(" Order updated: " + updated);
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void getAllOrders() {
        iOrderService.create(order);
        iOrderService.create(order1);
        List<Order> orderList = iOrderService.getAllOrders();
        assertNotNull(orderList);
        assertTrue(orderList.size() >= 2);
        System.out.println(" All Orders: " + orderList);
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void delete() {
        iOrderService.create(order);
        boolean deleted = iOrderService.delete("2255a");
        assertTrue(deleted);
        System.out.println("️ Order deleted");
    }
}