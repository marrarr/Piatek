package com.ZamianaRadianow.gra.controller;

import com.ZamianaRadianow.dto.GameDTO;
import com.ZamianaRadianow.dto.PlatformDTO;
import com.ZamianaRadianow.gra.model.Game;
import com.ZamianaRadianow.gra.model.Platform;
import com.ZamianaRadianow.gra.service.GameService;
import com.ZamianaRadianow.gra.service.PlatformService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/platforms")
public class PlatformController {

    private final GameService gameService;
    private final PlatformService platformService;

    public PlatformController(GameService gameService, PlatformService platformService) {
        this.gameService = gameService;
        this.platformService = platformService;
    }

    @PostMapping
    public ResponseEntity<Platform> createPlatform(@Valid @RequestBody PlatformDTO dto) {
        return new ResponseEntity<>(platformService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Platform> getPlatform(@PathVariable Long id) {
        return ResponseEntity.ok(platformService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Platform>> getAllPlatform() {
        return ResponseEntity.ok(platformService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Platform> updatePlatform(@PathVariable Long id, @Valid @RequestBody PlatformDTO dto) {
        return ResponseEntity.ok(platformService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlatform(@PathVariable Long id) {
        platformService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

