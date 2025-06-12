package com.ZamianaRadianow.security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @GetMapping("/login")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("OK");
    }
}