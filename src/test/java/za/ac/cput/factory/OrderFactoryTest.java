package za.ac.cput.factory;
//Vumbhoni Clifford Mnisi
//222929456
//Group 3G

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.OrderItem;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderFactoryTest {


    List<OrderItem> orderedItemList = new ArrayList<OrderItem>();
    List<OrderItem> invalidList = new ArrayList<>();
    private static OrderItem orderItem1;
    private static OrderItem orderItem2;
    private static Order order, order1;
    @BeforeEach
    void setUp() {

        orderItem1 = OrderItemFactory.createOrderItem("2255a","Grey Pants",2,100.00);
        orderedItemList.add(orderItem1);

         order = OrderFactory.createOrder("2255a","12/08/2026",
                200.00,"pending", orderedItemList);

        orderItem2 = OrderItemFactory.createOrderItem("2255a","White Shirt",0,00.00);
        invalidList.add(orderItem2);

        order1 = OrderFactory.createOrder("2255a","12/08/2026",
                00.00,"pending", invalidList);

    }
    @Test
    @org.junit.jupiter.api.Order(1)
     void createOrder() {

        assertNotNull(order);
        System.out.println(order.toString());
    }
    @org.junit.jupiter.api.Order(2)
    @Test
    void createOrderFail() {
        assertNull(order1);
    }
}