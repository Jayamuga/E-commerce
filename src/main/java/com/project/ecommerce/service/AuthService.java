package com.project.ecommerce.service;

import com.project.ecommerce.model.User;
import com.project.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Used by Spring Security for authentication
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("🔍 Trying to load user with email: " + email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        System.out.println("✅ Loaded user: " + user.getEmail() + ", Role: " + user.getRole());

        return new org.springframework.security.core.userdetails.User(
        	    user.getEmail(),
        	    user.getPassword(), // ✅ let the PasswordEncoder handle it
        	    List.of(new SimpleGrantedAuthority(user.getRole()))
        	);

    }


    
    public boolean login(String email, String password) {
        return userRepository.findByEmail(email)
                .map(u -> u.getPassword().equals(password))
                .orElse(false);
    }


   
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public void registerUser(User user) {
        userRepository.save(user);
    }

    public boolean checkUserExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
