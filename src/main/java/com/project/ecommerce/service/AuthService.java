package com.project.ecommerce.service;

import com.project.ecommerce.model.User;
import com.project.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean login(String email, String password) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(email));

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(password)) {
                System.out.println("✅ Login successful for user: " + email);
                return true;
            } else {
                System.out.println("❌ Incorrect password for user: " + email);
            }
        } else {
            System.out.println("❌ No user found with email: " + email);
        }

        return false;
    }

    public boolean checkUserExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public void registerUser(User user) {
        userRepository.save(user);
        System.out.println("✅ Registered new user with email: " + user.getEmail());
    }
}
