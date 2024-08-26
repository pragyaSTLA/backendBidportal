package com.portal.bid.controller;

import com.portal.bid.entity.User;
import com.portal.bid.repository.UserRepository;
import com.portal.bid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
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
        }
        catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is inactive and cannot log in.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during login.");
        }
    }

//    @CrossOrigin(origins = "http://localhost:3000")
        @PostMapping("/create")
        public ResponseEntity<String> createUser(@RequestBody User user) {

            // Validate required fields
            if (user.getFirstName() == null || user.getFirstName().isEmpty() ||
                    user.getLastName() == null || user.getLastName().isEmpty() ||
                    user.getEmail() == null || user.getEmail().isEmpty() ||
                    user.getPasswordHash() == null || user.getPasswordHash().isEmpty() ||
                    user.getDepartmentId() == 0) { // Assuming 0 is not a valid departmentId

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Validation failed: All required fields must be provided.");
            }

            // Call the service layer to save the user
            User savedUser = userService.createUser(user);

            // Return the success response
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully.");
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
