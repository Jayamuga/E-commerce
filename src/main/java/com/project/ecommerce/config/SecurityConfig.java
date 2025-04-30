package com.project.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.project.ecommerce.service.AuthService;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public UserDetailsService userDetailsService(AuthService authService) {
	    return authService;
	}


    // ✅ Configure role-based access
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Allow unauthenticated access to common endpoints
                .requestMatchers("/auth/**","/cart/**", "/register","/auth/register", "/login", "/css/**", "/js/**").permitAll()

                // Admin-only routes (create, update, delete products)
                .requestMatchers("/admin/**","/products/add", "/products/delete/**").hasRole("ADMIN")

                // User-only routes (cart actions)
                .requestMatchers("/cart/**", "/checkout","/cart","/cart/add").hasRole("USER")

                // All authenticated users can view products
                .requestMatchers("/products", "/products/**").authenticated()

                // Everything else
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/products", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable()); // Only disable for development/test

        return http.build();
    }
}
