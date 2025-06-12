package com.ZamianaRadianow.gra.repository;

import com.ZamianaRadianow.gra.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

}