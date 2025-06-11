package com.ZamianaRadianow.gra.controller;

import com.ZamianaRadianow.gra.dto.GenreRequestDTO;
import com.ZamianaRadianow.gra.dto.GenreResponseDTO;
import com.ZamianaRadianow.gra.model.Genre;
import com.ZamianaRadianow.gra.service.GenreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping
    public ResponseEntity<GenreResponseDTO> createGenre(@Valid @RequestBody GenreRequestDTO dto) {
        Genre genre = genreService.create(dto);
        return new ResponseEntity<>(genreService.mapToDTO(genre), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreResponseDTO> getGenre(@PathVariable Long id) {
        Genre genre = genreService.getById(id);
        return ResponseEntity.ok(genreService.mapToDTO(genre));
    }

    @GetMapping
    public ResponseEntity<List<GenreResponseDTO>> getAllGenres() {
        List<Genre> genres = genreService.getAll();
        return ResponseEntity.ok(genreService.mapToDTO(genres));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenreResponseDTO> updateGenre(@PathVariable Long id, @Valid @RequestBody GenreRequestDTO dto) {
        Genre genre = genreService.update(id, dto);
        return ResponseEntity.ok(genreService.mapToDTO(genre));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        genreService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

