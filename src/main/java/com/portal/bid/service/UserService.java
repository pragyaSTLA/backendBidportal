

package com.portal.bid.service;

import com.portal.bid.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String username);
    Long saveUser(User user);
    User createUser(User user);
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    Optional<User> updateUser(Long id, User userDetails);
    public User findUserByEmail(String email) ;
    void loginUser(String email, String passwordHash,User u);
}
