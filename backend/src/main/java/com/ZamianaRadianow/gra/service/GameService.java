package com.ZamianaRadianow.gra.service;

import com.ZamianaRadianow.dto.GameDTO;
import com.ZamianaRadianow.gra.model.Game;
import com.ZamianaRadianow.gra.repository.GameRepository;
import com.ZamianaRadianow.gra.repository.GenreRepository;
import com.ZamianaRadianow.gra.repository.PlatformRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final GenreRepository genreRepository;
    private final PlatformRepository platformRepository;

    public GameService(GameRepository gameRepository, GenreRepository genreRepository, PlatformRepository platformRepository) {
        this.gameRepository = gameRepository;
        this.genreRepository = genreRepository;
        this.platformRepository = platformRepository;
    }

    public Game create(GameDTO dto) {
        Game game = mapToEntity(dto);
        return gameRepository.save(game);
    }

    public Game getById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Game not found"));
    }

    public List<Game> getAll() {
        return gameRepository.findAll();
    }

    public Game update(Long id, GameDTO dto) {
        Game game = getById(id);
        Game updated = mapToEntity(dto);
        updated.setId(id);
        return gameRepository.save(updated);
    }

    public void delete(Long id) {
        gameRepository.deleteById(id);
    }

    private Game mapToEntity(GameDTO dto) {
        Game game = new Game();
        game.setTitle(dto.getTitle());
        game.setReleaseDate(dto.getReleaseDate());
        game.setDeveloper(dto.getDeveloper());
        game.setPublisher(dto.getPublisher());
        game.setDescription(dto.getDescription());

        if (dto.getGenreIds() != null) {
            game.setGenres(new HashSet<>(genreRepository.findAllById(dto.getGenreIds())));
        }

        if (dto.getPlatformIds() != null) {
            game.setPlatforms(new HashSet<>(platformRepository.findAllById(dto.getPlatformIds())));
        }

        return game;
    }
}

