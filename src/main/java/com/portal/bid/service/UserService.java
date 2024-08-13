

package com.portal.bid.service;

import com.portal.bid.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    Optional<User> updateUser(Long id, User userDetails);

    void loginUser(String email, String passwordHash,User u);
}
