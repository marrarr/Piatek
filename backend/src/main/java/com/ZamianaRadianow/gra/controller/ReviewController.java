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
    public ResponseEntity<Review> createReview(@Valid @RequestBody ReviewRequestDTO dto) {
        return new ResponseEntity<>(reviewService.create(dto), HttpStatus.CREATED);
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
        List<ReviewResponseDTO> dtoList = new ArrayList<>();
        for (Review r : reviewRepository.findAllByGameId(id)) {
            dtoList.add(reviewService.mapToDTO(r));
        }
        return ResponseEntity.ok(dtoList);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @Valid @RequestBody ReviewRequestDTO dto) {
        return ResponseEntity.ok(reviewService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

