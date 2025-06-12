package com.ZamianaRadianow.security.user;

import com.ZamianaRadianow.gra.dto.GameResponseListDTO;
import com.ZamianaRadianow.gra.dto.ReviewRequestDTO;
import com.ZamianaRadianow.gra.model.Game;
import com.ZamianaRadianow.gra.service.GameService;
import com.ZamianaRadianow.security.dto.RegisterRequestDTO;
import com.ZamianaRadianow.security.dto.UserResponseDTO;
import com.ZamianaRadianow.security.rola.DBRole;
import com.ZamianaRadianow.security.rola.RoleRepository;
import com.ZamianaRadianow.security.user.DBUser;
import com.ZamianaRadianow.security.user.UserRepository;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<DBUser> users = userRepository.findAll();
        List<UserResponseDTO> dtoList = new ArrayList<>();
        for (DBUser u : users) {
            UserResponseDTO dto  = new UserResponseDTO();
            dto.setId(u.getId());
            dto.setUsername(u.getUsername());
            dtoList.add(dto);
        }

        return ResponseEntity.ok(dtoList);
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        DBUser user = userRepository.findById(id).orElse(null);

        if (user != null) {
            userRepository.delete(user);
            return ResponseEntity.noContent().build();
        } else {
            // TODO zwroc info odpowiednie
            return ResponseEntity.noContent().build();
        }
    }
}