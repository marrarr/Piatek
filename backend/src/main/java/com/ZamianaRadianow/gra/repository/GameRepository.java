package com.ZamianaRadianow.gra.repository;

import com.ZamianaRadianow.gra.model.Game;
import com.ZamianaRadianow.zamiana.model.Zamiana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

}