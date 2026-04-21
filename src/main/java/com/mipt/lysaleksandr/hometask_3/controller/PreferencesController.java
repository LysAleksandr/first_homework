package com.mipt.lysaleksandr.hometask_3.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v3/preferences")
public class PreferencesController {

    private static final String VIEW_PREFERENCE_COOKIE = "viewPreference";

    @GetMapping("/view")
    public ResponseEntity<String> getViewPreference(
        @CookieValue(name = VIEW_PREFERENCE_COOKIE, defaultValue = "detailed") String mode) {
        return ResponseEntity.ok(mode);
    }

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