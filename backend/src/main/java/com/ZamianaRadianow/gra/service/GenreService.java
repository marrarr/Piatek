package com.ZamianaRadianow.gra.service;

import com.ZamianaRadianow.dto.GameDTO;
import com.ZamianaRadianow.dto.GenreDTO;
import com.ZamianaRadianow.gra.model.Game;
import com.ZamianaRadianow.gra.model.Genre;
import com.ZamianaRadianow.gra.repository.GameRepository;
import com.ZamianaRadianow.gra.repository.GenreRepository;
import com.ZamianaRadianow.gra.repository.PlatformRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class GenreService {

    private final GameRepository gameRepository;
    private final GenreRepository genreRepository;
    private final PlatformRepository platformRepository;

    public GenreService(GameRepository gameRepository, GenreRepository genreRepository, PlatformRepository platformRepository) {
        this.gameRepository = gameRepository;
        this.genreRepository = genreRepository;
        this.platformRepository = platformRepository;
    }

    public Genre create(GenreDTO dto) {
        Genre genre = mapToEntity(dto);
        return genreRepository.save(genre);
    }

    public Genre getById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genre not found"));
    }

    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    public Genre update(Long id, GenreDTO dto) {
        Genre genre = getById(id);
        Genre updated = mapToEntity(dto);
        updated.setId(id);
        return genreRepository.save(updated);
    }

    public void delete(Long id) {
        genreRepository.deleteById(id);
    }

    private Genre mapToEntity(GenreDTO dto) {
        Genre genre = new Genre();
        genre.setName(dto.getName());

        return genre;
    }
}

