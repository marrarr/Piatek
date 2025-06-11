package com.ZamianaRadianow.gra.repository;

import com.ZamianaRadianow.gra.model.Game;
import com.ZamianaRadianow.gra.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}