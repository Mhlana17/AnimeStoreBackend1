package za.ac.cput.controller;
//Vumbhoni Clifford Mnisi
//222929456
//Group 3G

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.OrderItem;
import za.ac.cput.factory.OrderFactory;
import za.ac.cput.factory.OrderItemFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String BASE_URL;  // Not static - will be set in @BeforeEach
    private static Order createdOrder;

    @BeforeEach
    void setUp() {
        // Use the injected port
        BASE_URL = "http://localhost:" + port + "/animeStore/api/orders";
        System.out.println("BASE_URL: " + BASE_URL);
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void createOrder() {
        OrderItem item1 = OrderItemFactory.createOrderItem("ITEM001", "Grey Pants", 2, 100.00);
        List<OrderItem> items = new ArrayList<>();
        items.add(item1);

        Order order = OrderFactory.createOrder("ORD001", "2026-08-16", 200.00, "PENDING", items);

        ResponseEntity<Order> response = restTemplate.postForEntity(
                BASE_URL + "/create",
                order,
                Order.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        createdOrder = response.getBody();
        System.out.println("Order created: " + response.getBody());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void getOrderById() {
        createOrder();

        ResponseEntity<Order> response = restTemplate.getForEntity(
                BASE_URL + "/" + createdOrder.getOrderId(),
                Order.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println("Order found: " + response.getBody());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void updateOrder() {
        createOrder();

        Order updatedOrder = new Order.Builder()
                .copy(createdOrder)
                .setStatus("COMPLETED")
                .build();

        HttpEntity<Order> request = new HttpEntity<>(updatedOrder);
        ResponseEntity<Order> response = restTemplate.exchange(
                BASE_URL + "/update",
                HttpMethod.PUT,
                request,
                Order.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("COMPLETED", response.getBody().getStatus());
        System.out.println("Order updated: " + response.getBody());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void getAllOrders() {
        createOrder();

        ResponseEntity<Order[]> response = restTemplate.getForEntity(
                BASE_URL + "/all",
                Order[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        System.out.println("All orders count: " + response.getBody().length);
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void getOrdersByStatus() {
        createOrder();

        ResponseEntity<Order[]> response = restTemplate.getForEntity(
                BASE_URL + "/status/PENDING",
                Order[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println("PENDING orders count: " + response.getBody().length);
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void deleteOrder() {
        createOrder();

        restTemplate.delete(BASE_URL + "/" + createdOrder.getOrderId());

        ResponseEntity<Order> response = restTemplate.getForEntity(
                BASE_URL + "/" + createdOrder.getOrderId(),
                Order.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("Order deleted successfully");
    }
}