package com.example.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserDetailsConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        var manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
            .password(new BCryptPasswordEncoder().encode("password"))
            .roles("USER")
            .authorities("NO_PRIVILEGE")
            .build());
        manager.createUser(User.withUsername("reader")
            .password(new BCryptPasswordEncoder().encode("password"))
            .roles("USER")
            .authorities("READ_PRIVILEGE")
            .build());
        return manager;
    }
}