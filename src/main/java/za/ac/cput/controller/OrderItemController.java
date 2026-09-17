package za.ac.cput.controller;
//Vumbhoni Clifford Mnisi
//222929456
//Group 3G
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.OrderItem;
import za.ac.cput.service.IOrderItemService;

import java.util.List;

@RestController
@RequestMapping("/api/orderitems")
@CrossOrigin(origins = "*")
public class OrderItemController {

    @Autowired
    private IOrderItemService orderItemService;

    // CREATE
    @PostMapping("/create")
    public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItem orderItem) {
        try {
            OrderItem created = orderItemService.create(orderItem);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ by ID
    @GetMapping("/{orderitemId}")
    public ResponseEntity<OrderItem> getOrderItemById(
            @PathVariable("orderitemId") String id) {

        return orderItemService.read(id)
                .map(item -> new ResponseEntity<>(item, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // UPDATE
    @PutMapping("/update")
    public ResponseEntity<OrderItem> updateOrderItem(@RequestBody OrderItem orderItem) {
        try {
            OrderItem updated = orderItemService.update(orderItem);
            if (updated != null) {
                return new ResponseEntity<>(updated, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE
    @DeleteMapping("/{orderitemId}")
    public ResponseEntity<HttpStatus> deleteOrderItem(@PathVariable("orderitemId") String id) {
        try {
            boolean deleted = orderItemService.delete(id);
            if (deleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET ALL
    @GetMapping("/all")
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        try {
            List<OrderItem> items = orderItemService.getAllOrderItems();
            if (items.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET BY DESCRIPTION (useful for searching)
    @GetMapping("/search")
    public ResponseEntity<List<OrderItem>> getOrderItemsByDescription(@RequestParam String description) {
        try {
            List<OrderItem> items = orderItemService.getOrderItemsByDescription(description);
            if (items.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}