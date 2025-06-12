package com.ZamianaRadianow.security;

import com.ZamianaRadianow.gra.dto.ReviewRequestDTO;
import com.ZamianaRadianow.security.dto.RegisterRequestDTO;
import com.ZamianaRadianow.security.rola.DBRole;
import com.ZamianaRadianow.security.rola.RoleRepository;
import com.ZamianaRadianow.security.user.DBUser;
import com.ZamianaRadianow.security.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/login")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("OK");
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