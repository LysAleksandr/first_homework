package com.example.gateway.api;

import com.example.gateway.dto.AuthRequest;
import com.example.gateway.dto.AuthResponse;
import com.example.gateway.security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collection;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        UserDetails user = (UserDetails) auth.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        String role = authorities.stream()
            .filter(a -> a.getAuthority().startsWith("ROLE_"))
            .findFirst()
            .map(a -> a.getAuthority().substring(5))
            .orElse("USER");

        String authority = authorities.stream()
            .filter(a -> !a.getAuthority().startsWith("ROLE_"))
            .findFirst()
            .map(GrantedAuthority::getAuthority)
            .orElse("");

        String token = jwtUtils.generateToken(user.getUsername(), role, authority);
        return new AuthResponse(token);
    }
}