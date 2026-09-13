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
class OrderItemServiceTest {

    @Autowired
    private IOrderItemService iOrderItemService;

    @Autowired
    private IOrderService orderService;  // ADD THIS

    private OrderItem orderItem1;
    private OrderItem orderItem2;
    private Order order;
    private Order savedOrder;  // ADD THIS

    @BeforeEach
    void setUp() {
        orderItem1 = OrderItemFactory.createOrderItem(
                "ANI001",
                "Naruto Action Figure",
                2,
                350.00
        );

        orderItem2 = OrderItemFactory.createOrderItem(
                "ANI002",
                "One Piece Wanted Poster",
                1,
                150.00
        );

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);

        order = OrderFactory.createOrder(
                "ORD001",
                "12/08/2026",
                850.00,
                "PENDING",
                orderItems
        );

        orderItem1.setOrder(order);
        orderItem2.setOrder(order);

        // SAVE THE ORDER FIRST
        savedOrder = orderService.create(order);
        assertNotNull(savedOrder, "Order must be saved first");
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void create() {
        // Use the saved order
        orderItem1.setOrder(savedOrder);

        OrderItem created = iOrderItemService.create(orderItem1);
        assertNotNull(created);
        assertEquals("ANI001", created.getOrderItemId());
        System.out.println("Order Item created: " + created);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void read() {
        orderItem1.setOrder(savedOrder);
        iOrderItemService.create(orderItem1);

        OrderItem found = iOrderItemService.read("ANI001");
        assertNotNull(found);
        System.out.println("Order Item found: " + found);
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void update() {
        orderItem1.setOrder(savedOrder);
        iOrderItemService.create(orderItem1);

        OrderItem updatedOrderItem = new OrderItem.Builder()
                .copy(orderItem1)
                .setItemDescription("Naruto Shippuden Figure")
                .setItemQuantity(3)
                .setUnitPrice(400.00)
                .build();

        updatedOrderItem.setOrder(savedOrder);  // Use saved order
        OrderItem updated = iOrderItemService.update(updatedOrderItem);

        assertNotNull(updated);
        assertEquals("Naruto Shippuden Figure", updated.getItemDescription());
        System.out.println("Order Item updated: " + updated);
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void getAllOrderItems() {
        orderItem1.setOrder(savedOrder);
        orderItem2.setOrder(savedOrder);
        iOrderItemService.create(orderItem1);
        iOrderItemService.create(orderItem2);

        List<OrderItem> orderItems = iOrderItemService.getAllOrderItems();
        assertNotNull(orderItems);
        assertTrue(orderItems.size() >= 2);
        System.out.println("All Order Items: " + orderItems);
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void delete() {
        orderItem1.setOrder(savedOrder);
        iOrderItemService.create(orderItem1);

        boolean deleted = iOrderItemService.delete("ANI001");
        assertTrue(deleted);
        System.out.println("Order Item deleted");
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void findByItemDescription() {
        orderItem1.setOrder(savedOrder);
        orderItem2.setOrder(savedOrder);
        iOrderItemService.create(orderItem1);
        iOrderItemService.create(orderItem2);

        List<OrderItem> foundItems = iOrderItemService.getOrderItemsByDescription("Naruto Action Figure");
        assertNotNull(foundItems);
        assertFalse(foundItems.isEmpty());
        assertEquals("Naruto Action Figure", foundItems.get(0).getItemDescription());
        System.out.println("Order items found by description: " + foundItems);
    }
}