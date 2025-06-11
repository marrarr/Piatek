package com.ZamianaRadianow.gra.controller;

import com.ZamianaRadianow.gra.dto.ReviewRequestDTO;
import com.ZamianaRadianow.gra.dto.ReviewResponseDTO;
import com.ZamianaRadianow.gra.model.Review;
import com.ZamianaRadianow.gra.repository.ReviewRepository;
import com.ZamianaRadianow.gra.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;

    public ReviewController(ReviewService reviewService, ReviewRepository reviewRepository) {
        this.reviewService = reviewService;
        this.reviewRepository = reviewRepository;
    }


    @PostMapping
    public ResponseEntity<ReviewResponseDTO> createReview(@Valid @RequestBody ReviewRequestDTO dto) {
        Review review = reviewService.create(dto);
        return new ResponseEntity<>(reviewService.mapToDTO(review), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO> getReview(@PathVariable Long id) {
        Review review = reviewService.getById(id);
        return ResponseEntity.ok(reviewService.mapToDTO(review));
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAll());
    }

    @GetMapping("/game/{id}")
    public ResponseEntity<List<ReviewResponseDTO>> getAllReviewsForGame(@PathVariable Long id) {
        List<Review> reviews = reviewService.getAllByGameId(id);
        return ResponseEntity.ok(reviewService.mapToDTO(reviews));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO> updateReview(@PathVariable Long id, @Valid @RequestBody ReviewRequestDTO dto) {
        Review review = reviewService.update(id, dto);
        return ResponseEntity.ok(reviewService.mapToDTO(review));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

