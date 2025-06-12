package com.ZamianaRadianow.security.controller;

import com.ZamianaRadianow.security.config.JwtUtil;
import com.ZamianaRadianow.security.dto.AuthenticationRequest;
import com.ZamianaRadianow.security.dto.AuthenticationResponse;
import com.ZamianaRadianow.security.dto.RegisterRequestDTO;
import com.ZamianaRadianow.security.rola.DBRole;
import com.ZamianaRadianow.security.rola.RoleRepository;
import com.ZamianaRadianow.security.user.DBUser;
import com.ZamianaRadianow.security.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails);


            //
            DBUser user = userRepository.findByUsername(userDetails.getUsername());
            Long id = user.getId();
            //

            return ResponseEntity.ok(new AuthenticationResponse(jwt, id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO dto) {
        // 1. Sprawdzenie czy hasła są takie same
        if (!dto.getPassword().equals(dto.getSecondPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body("Hasła nie są identyczne");
        }

        // 2. Sprawdzenie czy użytkownik już istnieje
        if (userRepository.existsByUsername(dto.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Nazwa użytkownika jest już zajęta");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Email jest już zarejestrowany");
        }

        // 3. Znajdź rolę USER (możesz też stworzyć jeśli nie istnieje)
        DBRole role = roleRepository.findByName("USER");

        // 4. Stwórz i zapisz nowego użytkownika
        DBUser newUser = new DBUser();
        newUser.setUsername(dto.getUsername());
        newUser.setEmail(dto.getEmail());
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        newUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        newUser.getRoles().add(role);

        userRepository.save(newUser);

        // 5. Zwróć odpowiedź
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", "Rejestracja zakończona pomyślnie",
                        "username", newUser.getUsername()
                ));
    }


}