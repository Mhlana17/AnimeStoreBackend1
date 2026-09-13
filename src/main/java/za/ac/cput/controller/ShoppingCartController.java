package za.ac.cput.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import za.ac.cput.domain.ShoppingCart;
import za.ac.cput.service.IShoppingCartService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shopping-carts")
@CrossOrigin(origins = "*")
public class ShoppingCartController {

    private final IShoppingCartService shoppingCartService;

    public ShoppingCartController(IShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/create")
    public ResponseEntity<ShoppingCart> create(@RequestBody ShoppingCart shoppingCart) {
        return ResponseEntity.status(HttpStatus.CREATED).body(shoppingCartService.create(shoppingCart));
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<ShoppingCart> read(@PathVariable String cartId) {
        Optional<ShoppingCart> shoppingCart = shoppingCartService.read(cartId);
        return shoppingCart.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update")
    public ResponseEntity<ShoppingCart> update(@RequestBody ShoppingCart shoppingCart) {
        ShoppingCart updatedShoppingCart = shoppingCartService.update(shoppingCart);
        if (updatedShoppingCart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedShoppingCart);
    }

    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<Void> delete(@PathVariable String cartId) {
        if (shoppingCartService.delete(cartId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<ShoppingCart>> getAll() {
        return ResponseEntity.ok(shoppingCartService.getAll());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ShoppingCart>> getByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(shoppingCartService.getShoppingCartsByUserId(userId));
    }

    @GetMapping("/status")
    public ResponseEntity<List<ShoppingCart>> getByStatus(@RequestParam String status) {
        return ResponseEntity.ok(shoppingCartService.getShoppingCartsByStatus(status));
    }
}