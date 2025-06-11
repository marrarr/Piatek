package com.ZamianaRadianow.gra.controller;

import com.ZamianaRadianow.gra.dto.GameRequestDTO;
import com.ZamianaRadianow.gra.dto.GameResponseDetailsDTO;
import com.ZamianaRadianow.gra.dto.GameResponseListDTO;
import com.ZamianaRadianow.gra.model.Game;
import com.ZamianaRadianow.gra.service.GameService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<Game> createGame(@Valid @RequestBody GameRequestDTO dto) {
        return new ResponseEntity<>(gameService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameResponseDetailsDTO> getGame(@PathVariable Long id) {
        Game game = gameService.getById(id);
        return ResponseEntity.ok(gameService.mapToDetailsDTO(game));
    }

    @GetMapping
    public ResponseEntity<List<GameResponseListDTO>> getAllGames() {
        List<GameResponseListDTO> dtoList = new ArrayList<>();
        for (Game g : gameService.getAll()) {
            dtoList.add(gameService.mapToListDTO(g));
        }

        return ResponseEntity.ok(dtoList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable Long id, @Valid @RequestBody GameRequestDTO dto) {
        return ResponseEntity.ok(gameService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        gameService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

