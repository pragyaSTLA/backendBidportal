package com.portal.bid.service.implementation;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.portal.bid.entity.User;
import com.portal.bid.repository.UserRepository;
import com.portal.bid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public User createUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(encodedPassword);

        // Save the user to the repository
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public Optional<User> updateUser(Long id, User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setEmployeeId(userDetails.getEmployeeId());
            user.setFirstName(userDetails.getFirstName());
            user.setLastName(userDetails.getLastName());
            user.setEmail(userDetails.getEmail());
            user.setMobile(userDetails.getMobile());
            user.setDepartmentId(userDetails.getDepartmentId());
            user.setRole(userDetails.getRole());
            user.setStatus(userDetails.getStatus());
            // Only update the password if a new password is provided
            if (userDetails.getPasswordHash() != null && !userDetails.getPasswordHash().isEmpty()) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                user.setPasswordHash(passwordEncoder.encode(userDetails.getPasswordHash()));
            }
            return userRepository.save(user);
        });
    }



    public void loginUser(String email, String plainPassword,User u) {
        // Retrieve the user by email from the database
        User storedUser = userRepository.findByEmail(email);
        System.out.println(storedUser); // Logs the user object

        // Check if the user exists
        if (storedUser == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        String encodedPassword = passwordEncoder.encode(u.getPasswordHash());
//        user.setPasswordHash(encodedPassword);

        // Verify the password
        System.out.println(encodedPassword + " " + storedUser.getPasswordHash());
        System.out.println(plainPassword+ " " + storedUser.getPasswordHash());


        if (!passwordEncoder.matches(encodedPassword, storedUser.getPasswordHash())) {
            System.out.println("nbdfvddfkj");
        }
        if (!passwordEncoder.matches(plainPassword, storedUser.getPasswordHash())) {
            System.out.println(plainPassword + " " + storedUser.getPasswordHash());
            throw new BadCredentialsException("Invalid password");
        }

        // Proceed with additional login steps if necessary
    }
}
