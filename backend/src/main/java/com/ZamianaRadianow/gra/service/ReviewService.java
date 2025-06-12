package com.ZamianaRadianow.gra.service;

import com.ZamianaRadianow.gra.dto.ReviewRequestDTO;
import com.ZamianaRadianow.gra.dto.ReviewResponseDTO;
import com.ZamianaRadianow.gra.model.Review;
import com.ZamianaRadianow.gra.repository.GameRepository;
import com.ZamianaRadianow.gra.repository.GenreRepository;
import com.ZamianaRadianow.gra.repository.PlatformRepository;
import com.ZamianaRadianow.gra.repository.ReviewRepository;
import com.ZamianaRadianow.security.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ReviewService {

    private final GameRepository gameRepository;
    private final GenreRepository genreRepository;
    private final PlatformRepository platformRepository;
    private final ReviewRepository reviewRepository;
    private final GameService gameService;
    private final UserRepository userRepository;


    public ReviewService(GameRepository gameRepository, GenreRepository genreRepository, PlatformRepository platformRepository, ReviewRepository reviewRepository, GameService gameService, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.genreRepository = genreRepository;
        this.platformRepository = platformRepository;
        this.reviewRepository = reviewRepository;
        this.gameService = gameService;
        this.userRepository = userRepository;
    }

    public Review create(ReviewRequestDTO dto) {
        Review review = mapToEntity(dto);
        review.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return reviewRepository.save(review);
    }

    public Review getById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("REview not found"));
    }

    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    public List<Review> getAllByGameId(Long id) {
        return reviewRepository.findAllByGameId(id);
    }

    public Review update(Long id, ReviewRequestDTO dto) {
        Review review = getById(id);
        Review updated = mapToEntity(dto);
        updated.setId(id);
        return reviewRepository.save(updated);
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

    public Review mapToEntity(ReviewRequestDTO dto) {
        Review review = new Review();
        review.setReviewText(dto.getReviewText());
        review.setRating(dto.getRating());
        review.setGame(gameService.getById(dto.getGameId()));
        review.setUser(userRepository.findById(dto.getUserId()).orElseThrow()); // TODO service

        return review;
    }

    public ReviewResponseDTO mapToDTO(Review review) {
        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.setId(review.getId());
        dto.setGameId(review.getGame().getId());
        dto.setUserId(review.getUser().getId());
        dto.setRating(review.getRating());
        dto.setReviewText(review.getReviewText());

        return dto;
    }

    public List<ReviewResponseDTO> mapToDTO(List<Review> reviews) {
        return reviews.stream().map(this::mapToDTO).toList();
    }
}

