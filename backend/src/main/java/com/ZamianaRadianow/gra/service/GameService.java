package com.ZamianaRadianow.gra.service;

import com.ZamianaRadianow.gra.dto.*;
import com.ZamianaRadianow.gra.model.Game;
import com.ZamianaRadianow.gra.repository.GameRepository;
import com.ZamianaRadianow.gra.repository.GenreRepository;
import com.ZamianaRadianow.gra.repository.PlatformRepository;
import com.ZamianaRadianow.gra.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        double rating = (reviewRepository.findAverageRatingByGameId(game.getId()) == null) ? 0.0 : reviewRepository.findAverageRatingByGameId(game.getId());

        GameResponseDetailsDTO dto = new GameResponseDetailsDTO();
        dto.setId(game.getId());
        dto.setTitle(game.getTitle());
        dto.setAverageRating(rating);
        dto.setDescription(game.getDescription());
        dto.setReleaseDate(game.getReleaseDate());
        dto.setDeveloper(game.getDeveloper());
        dto.setPublisher(game.getPublisher());

        // Mapowanie gatunków
        if (game.getGenres() != null) {
            Set<GenreResponseDTO> genreDTOs = game.getGenres().stream()
                    .map(genre -> {
                        GenreResponseDTO genreDTO = new GenreResponseDTO();
                        genreDTO.setId(genre.getId());
                        genreDTO.setName(genre.getName());
                        return genreDTO;
                    })
                    .collect(Collectors.toSet());
            dto.setGenres(genreDTOs);
        }

        // Mapowanie platform
        if (game.getPlatforms() != null) {
            Set<PlatformResponseDTO> platformDTOs = game.getPlatforms().stream()
                    .map(platform -> {
                        PlatformResponseDTO platformDTO = new PlatformResponseDTO();
                        platformDTO.setId(platform.getId());
                        platformDTO.setName(platform.getName());
                        return platformDTO;
                    })
                    .collect(Collectors.toSet());
            dto.setPlatforms(platformDTOs);
        }

        return dto;
    }

    public GameResponseListDTO mapToListDTO(Game game) {
        double rating = reviewRepository.findAverageRatingByGameId(game.getId());
        GameResponseListDTO dto = new GameResponseListDTO();
        dto.setId(game.getId());
        dto.setTitle(game.getTitle());
        dto.setAverageRating(rating);

//        if (game.getImage() != null) {
//            ImageResponseDTO imageDto = new ImageResponseDTO();
//            imageDto.setId(game.getImage().getId());
//            imageDto.setContentType(game.getImage().getContentType());
//            imageDto.setData(game.getImage().getData());
//            dto.setImage(imageDto);
//        } else {
//            dto.setImage(null);
//        }


        return dto;
    }

    public List<GameResponseListDTO> mapToListDTO(List<Game> games) {
        return games.stream().map(this::mapToListDTO).toList();
    }
}