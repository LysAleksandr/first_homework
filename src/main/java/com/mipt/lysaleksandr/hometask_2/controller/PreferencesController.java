package com.mipt.lysaleksandr.hometask_2.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/preferences")
public class PreferencesController {

    private static final String VIEW_PREFERENCE_COOKIE = "viewPreference";

    @Operation(summary = "Get view preference from cookie")
    @GetMapping("/view")
    public ResponseEntity<String> getViewPreference(
        @CookieValue(name = VIEW_PREFERENCE_COOKIE, defaultValue = "detailed") String mode) {
        return ResponseEntity.ok(mode);
    }

    @Operation(summary = "Set view preference cookie")
    @PostMapping("/view")
    public ResponseEntity<Void> setViewPreference(@RequestParam String mode,
        HttpServletResponse response) {
        Cookie cookie = new Cookie(VIEW_PREFERENCE_COOKIE, mode);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 365);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }
}