package com.ZamianaRadianow.gra.repository;

import com.ZamianaRadianow.gra.model.Game;
import com.ZamianaRadianow.gra.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}