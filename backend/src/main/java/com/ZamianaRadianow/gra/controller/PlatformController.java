package com.ZamianaRadianow.gra.controller;

import com.ZamianaRadianow.gra.dto.PlatformRequestDTO;
import com.ZamianaRadianow.gra.dto.PlatformResponseDTO;
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
    public ResponseEntity<PlatformResponseDTO> createPlatform(@Valid @RequestBody PlatformRequestDTO dto) {
        Platform platform = platformService.create(dto);
        return new ResponseEntity<>(platformService.mapToDTO(platform), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlatformResponseDTO> getPlatform(@PathVariable Long id) {
        Platform platform = platformService.getById(id);
        return ResponseEntity.ok(platformService.mapToDTO(platform));
    }

    @GetMapping
    public ResponseEntity<List<PlatformResponseDTO>> getAllPlatform() {
        List<Platform> platforms = platformService.getAll();
        return ResponseEntity.ok(platformService.mapToDTO(platforms));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlatformResponseDTO> updatePlatform(@PathVariable Long id, @Valid @RequestBody PlatformRequestDTO dto) {
        Platform platform = platformService.update(id, dto);
        return ResponseEntity.ok(platformService.mapToDTO(platform));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlatform(@PathVariable Long id) {
        platformService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

