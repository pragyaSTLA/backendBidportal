package com.portal.bid.controller;

import com.portal.bid.entity.User;
import com.portal.bid.repository.UserRepository;
import com.portal.bid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {


    @Autowired
    private UserService userService;

//    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User u){
        try {

            userService.loginUser(u.getEmail(),u.getPasswordHash(),u);

            return ResponseEntity.status(HttpStatus.OK).body("Login successful.");
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during login.");
        }
    }

//    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.createUser(user);
        return ResponseEntity.ok(savedUser);
    }
//    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
//    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

//    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> updatedUser = userService.updateUser(id, userDetails);
        if (updatedUser.isPresent()) {
            return ResponseEntity.ok(updatedUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
