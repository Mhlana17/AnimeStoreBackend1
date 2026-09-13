//Author: Phihlello Junaid Maroga 219354359
package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.User;
import za.ac.cput.dto.LoginRequest;
import za.ac.cput.service.IUserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.create(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<User> readUser(@PathVariable String userId) {
        Optional<User> user = userService.read(userId);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody User user) {
        Optional<User> existingUser = userService.read(userId);
        if (existingUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setUserId(userId);
        User updatedUser = userService.update(user);
        if (updatedUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        boolean deleted = userService.delete(userId);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest request) {
        Optional<User> user = userService.login(request.email(), request.password());
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }

    @GetMapping("/search/username/{userName}")
    public ResponseEntity<List<User>> searchByUserName(@PathVariable String userName) {
        List<User> users = userService.searchByUserName(userName);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @GetMapping("/search/email/{email}")
    public ResponseEntity<User> searchByEmail(@PathVariable String email) {
        Optional<User> user = userService.searchByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/search/pattern")
    public ResponseEntity<List<User>> searchByPattern(@RequestParam String pattern) {
        List<User> users = userService.searchUsersByPattern(pattern);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}