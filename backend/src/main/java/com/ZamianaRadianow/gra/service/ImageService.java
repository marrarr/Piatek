//package com.ZamianaRadianow.gra.service;
//
//
//import com.ZamianaRadianow.gra.dto.ImageResponseDTO;
//import com.ZamianaRadianow.gra.model.Game;
//import com.ZamianaRadianow.gra.model.Image;
//import com.ZamianaRadianow.gra.repository.GameRepository;
//import com.ZamianaRadianow.gra.repository.ImageRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//@Service
//public class ImageService {
//    private final ImageRepository imageRepository;
//    private final GameRepository gameRepository;
//
//    public ImageService(ImageRepository imageRepository, GameRepository gameRepository) {
//        this.imageRepository = imageRepository;
//        this.gameRepository = gameRepository;
//    }
//
//    public ImageResponseDTO saveImage(MultipartFile file, Long gameId) throws IOException {
//        Game game = gameRepository.findById(gameId)
//                .orElseThrow(() -> new IllegalArgumentException("Gra nie istnieje"));
//
//        Image image = new Image();
//        image.setData(file.getBytes());
//        image.setContentType(file.getContentType());
//        image.setGame(game);
//
//        Image saved = imageRepository.save(image);
//
//        ImageResponseDTO dto = new ImageResponseDTO();
//        dto.setId(saved.getId());
//        dto.setContentType(saved.getContentType());
//        dto.setData(saved.getData());
//
//        return dto;
//    }
//
//    public ImageResponseDTO getImageById(Long id) {
//        Image image = imageRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Obraz nie istnieje"));
//
//        ImageResponseDTO dto = new ImageResponseDTO();
//        dto.setId(image.getId());
//        dto.setContentType(image.getContentType());
//        dto.setData(image.getData());
//
//        return dto;
//    }
//}
//
