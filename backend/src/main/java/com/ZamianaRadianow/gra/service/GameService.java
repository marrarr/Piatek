package com.ZamianaRadianow.gra.service;

import com.ZamianaRadianow.gra.dto.GameRequestDTO;
import com.ZamianaRadianow.gra.dto.GameResponseDetailsDTO;
import com.ZamianaRadianow.gra.dto.GameResponseListDTO;
import com.ZamianaRadianow.gra.model.Game;
import com.ZamianaRadianow.gra.repository.GameRepository;
import com.ZamianaRadianow.gra.repository.GenreRepository;
import com.ZamianaRadianow.gra.repository.PlatformRepository;
import com.ZamianaRadianow.gra.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final GenreRepository genreRepository;
    private final PlatformRepository platformRepository;
    private final ReviewRepository reviewRepository;

    public GameService(GameRepository gameRepository, GenreRepository genreRepository, PlatformRepository platformRepository, ReviewRepository reviewRepository) {
        this.gameRepository = gameRepository;
        this.genreRepository = genreRepository;
        this.platformRepository = platformRepository;
        this.reviewRepository = reviewRepository;
    }

    public Game create(GameRequestDTO dto) {
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

    public Game update(Long id, GameRequestDTO dto) {
        Game game = getById(id);
        Game updated = mapToEntity(dto);
        updated.setId(id);
        return gameRepository.save(updated);
    }

    public void delete(Long id) {
        gameRepository.deleteById(id);
    }

    public Game mapToEntity(GameRequestDTO dto) {
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

    public GameResponseDetailsDTO mapToDetailsDTO(Game game) {
        double rating = reviewRepository.findAverageRatingByGameId(game.getId());
        GameResponseDetailsDTO dto = new GameResponseDetailsDTO();
        dto.setId(game.getId());
        dto.setTitle(game.getTitle());
        dto.setAverageRating(rating);
        dto.setDescription(game.getDescription());
        dto.setReleaseDate(game.getReleaseDate());
        dto.setDeveloper(game.getDeveloper());
        dto.setPublisher(game.getPublisher());
        dto.setDescription(game.getDescription());
        
        return dto;
    }

    public GameResponseListDTO mapToListDTO(Game game) {
        double rating = reviewRepository.findAverageRatingByGameId(game.getId());
        GameResponseListDTO dto = new GameResponseListDTO();
        dto.setTitle(game.getTitle());
        dto.setAverageRating(rating);

        return dto;
    }
}

