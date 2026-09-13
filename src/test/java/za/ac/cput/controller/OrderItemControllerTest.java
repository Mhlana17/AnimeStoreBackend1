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
class OrderItemControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String BASE_URL;    // /animeStore/api/orderitems
    private String ORDER_URL;   // /animeStore/api/orders
    private static OrderItem createdOrderItem;
    private static Order createdOrder;

    @BeforeEach
    void setUp() {
        BASE_URL = "http://localhost:" + port + "/animeStore/api/orderitems";
        ORDER_URL = "http://localhost:" + port + "/animeStore/api/orders";
        System.out.println("OrderItem BASE_URL: " + BASE_URL);
        System.out.println("Order BASE_URL:     " + ORDER_URL);
    }

    /**
     * Helper: creates an Order via the Order API.
     * Required because OrderItem has a foreign key to Order.
     */
    private void createOrderForTest() {
        OrderItem tempItem = OrderItemFactory.createOrderItem("TMP001", "Temp", 1, 10.00);
        List<OrderItem> items = new ArrayList<>();
        items.add(tempItem);

        Order order = OrderFactory.createOrder("ORD100", "2026-08-16", 10.00, "PENDING", items);

        ResponseEntity<Order> response = restTemplate.postForEntity(
                ORDER_URL + "/create",
                order,
                Order.class
        );

        if (response.getStatusCode() == HttpStatus.CREATED && response.getBody() != null) {
            createdOrder = response.getBody();
            System.out.println("Test order created: " + createdOrder.getOrderId());
        } else {
            System.out.println("Failed to create test order. Status: " + response.getStatusCode());
        }
    }

    // ============================================================
    // 1. CREATE
    // POST /animeStore/api/orderitems/create
    // ============================================================
    @Test
    @org.junit.jupiter.api.Order(1)
    void createOrderItem() {
        // Parent Order must exist first (foreign key constraint)
        createOrderForTest();
        assertNotNull(createdOrder, "Parent Order must be created first");

        // Create OrderItem and link to saved Order
        OrderItem item = OrderItemFactory.createOrderItem(
                "ITEM001",
                "Grey Pants",
                2,
                100.00
        );
        item.setOrder(createdOrder);

        ResponseEntity<OrderItem> response = restTemplate.postForEntity(
                BASE_URL + "/create",
                item,
                OrderItem.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ITEM001", response.getBody().getOrderItemId());
        createdOrderItem = response.getBody();
        System.out.println("OrderItem created: " + response.getBody());
    }

    // ============================================================
    // 2. READ by ID
    // GET /animeStore/api/orderitems/{orderitemId}
    // ============================================================
    @Test
    @org.junit.jupiter.api.Order(2)
    void getOrderItemById() {
        createOrderItem();
        assertNotNull(createdOrderItem, "OrderItem must be created first");

        ResponseEntity<OrderItem> response = restTemplate.getForEntity(
                BASE_URL + "/" + createdOrderItem.getOrderItemId(),
                OrderItem.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdOrderItem.getOrderItemId(), response.getBody().getOrderItemId());
        System.out.println("📖 OrderItem found: " + response.getBody());
    }

    // ============================================================
    // 3. UPDATE
    // PUT /animeStore/api/orderitems/update
    // ============================================================
    @Test
    @org.junit.jupiter.api.Order(3)
    void updateOrderItem() {
        createOrderItem();
        assertNotNull(createdOrderItem);

        OrderItem updated = new OrderItem.Builder()
                .copy(createdOrderItem)
                .setItemDescription("Grey Pants - Updated")
                .setItemQuantity(3)
                .setUnitPrice(150.00)
                .build();

        HttpEntity<OrderItem> request = new HttpEntity<>(updated);

        ResponseEntity<OrderItem> response = restTemplate.exchange(
                BASE_URL + "/update",
                HttpMethod.PUT,
                request,
                OrderItem.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Grey Pants - Updated", response.getBody().getItemDescription());
        assertEquals(3, response.getBody().getItemQuantity());
        System.out.println("OrderItem updated: " + response.getBody());
    }

    // ============================================================
    // 4. DELETE
    // DELETE /animeStore/api/orderitems/{orderitemId}
    // ============================================================
    @Test
    @org.junit.jupiter.api.Order(4)
    void deleteOrderItem() {
        createOrderItem();
        assertNotNull(createdOrderItem);

        restTemplate.delete(BASE_URL + "/" + createdOrderItem.getOrderItemId());

        ResponseEntity<OrderItem> response = restTemplate.getForEntity(
                BASE_URL + "/" + createdOrderItem.getOrderItemId(),
                OrderItem.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("🗑️ OrderItem deleted successfully");
    }

    // ============================================================
    // 5. GET ALL
    // GET /animeStore/api/orderitems/all
    // ============================================================
    @Test
    @org.junit.jupiter.api.Order(5)
    void getAllOrderItems() {
        createOrderItem();

        ResponseEntity<OrderItem[]> response = restTemplate.getForEntity(
                BASE_URL + "/all",
                OrderItem[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        System.out.println("📋 All OrderItems count: " + response.getBody().length);
    }

    // ============================================================
    // 6. SEARCH by description
    // GET /animeStore/api/orderitems/search?description=Grey Pants
    // ============================================================
    @Test
    @org.junit.jupiter.api.Order(6)
    void getOrderItemsByDescription() {
        createOrderItem();

        String url = BASE_URL + "/search?description=Grey Pants";

        ResponseEntity<OrderItem[]> response = restTemplate.getForEntity(
                url,
                OrderItem[].class
        );

        // 200 OK if found, 204 NO_CONTENT if nothing matches
        assertTrue(response.getStatusCode() == HttpStatus.OK
                || response.getStatusCode() == HttpStatus.NO_CONTENT);

        if (response.getStatusCode() == HttpStatus.OK) {
            assertNotNull(response.getBody());
            assertTrue(response.getBody().length > 0);
            System.out.println("Found " + response.getBody().length
                    + " OrderItem(s) for description 'Grey Pants'");
        } else {
            System.out.println("No items matched 'Grey Pants'");
        }
    }
}