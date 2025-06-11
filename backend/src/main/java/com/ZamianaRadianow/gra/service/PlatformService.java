package com.ZamianaRadianow.gra.service;

import com.ZamianaRadianow.dto.PlatformDTO;
import com.ZamianaRadianow.gra.model.Platform;
import com.ZamianaRadianow.gra.repository.GameRepository;
import com.ZamianaRadianow.gra.repository.GenreRepository;
import com.ZamianaRadianow.gra.repository.PlatformRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatformService {

    private final GameRepository gameRepository;
    private final GenreRepository genreRepository;
    private final PlatformRepository platformRepository;

    public PlatformService(GameRepository gameRepository, GenreRepository genreRepository, PlatformRepository platformRepository) {
        this.gameRepository = gameRepository;
        this.genreRepository = genreRepository;
        this.platformRepository = platformRepository;
    }

    public Platform create(PlatformDTO dto) {
        Platform platform = mapToEntity(dto);
        return platformRepository.save(platform);
    }

    public Platform getById(Long id) {
        return platformRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Platform not found"));
    }

    public List<Platform> getAll() {
        return platformRepository.findAll();
    }

    public Platform update(Long id, PlatformDTO dto) {
        Platform platform = getById(id);
        Platform updated = mapToEntity(dto);
        updated.setId(id);
        return platformRepository.save(updated);
    }

    public void delete(Long id) {
        platformRepository.deleteById(id);
    }

    private Platform mapToEntity(PlatformDTO dto) {
        Platform platform = new Platform();
        platform.setName(dto.getName());

        return platform;
    }
}