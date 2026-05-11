package com.example.gateway.api;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ProfileController {

    @GetMapping("/profile")
    public Map<String, String> profile(@AuthenticationPrincipal UserDetails user) {
        return Map.of("username", user.getUsername(), "roles", user.getAuthorities().toString());
    }

    @GetMapping("/docs")
    public String docs() {
        return "Sensitive docs";
    }
}