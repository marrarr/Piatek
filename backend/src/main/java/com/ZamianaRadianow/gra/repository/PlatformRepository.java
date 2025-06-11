package com.ZamianaRadianow.gra.repository;

import com.ZamianaRadianow.gra.model.Game;
import com.ZamianaRadianow.gra.model.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformRepository extends JpaRepository<Platform, Long> {

}