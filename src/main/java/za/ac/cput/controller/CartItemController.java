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
import org.springframework.web.bind.annotation.RestController;
import za.ac.cput.domain.CartItem;
import za.ac.cput.service.ICartItemService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart-items")
@CrossOrigin(origins = "*")
public class CartItemController {

    private final ICartItemService cartItemService;

    public CartItemController(ICartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/create")
    public ResponseEntity<CartItem> create(@RequestBody CartItem cartItem) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemService.create(cartItem));
    }

    @GetMapping("/{cartItemId}")
    public ResponseEntity<CartItem> read(@PathVariable String cartItemId) {
        Optional<CartItem> cartItem = cartItemService.read(cartItemId);
        return cartItem.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update")
    public ResponseEntity<CartItem> update(@RequestBody CartItem cartItem) {
        CartItem updatedCartItem = cartItemService.update(cartItem);
        if (updatedCartItem == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCartItem);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<Void> delete(@PathVariable String cartItemId) {
        if (cartItemService.delete(cartItemId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<CartItem>> getAll() {
        return ResponseEntity.ok(cartItemService.getAll());
    }
}