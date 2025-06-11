package com.ZamianaRadianow.gra.repository;

import com.ZamianaRadianow.gra.model.Game;
import com.ZamianaRadianow.gra.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}