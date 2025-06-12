//package com.ZamianaRadianow.gra.controller;
//
//import com.ZamianaRadianow.gra.dto.ImageResponseDTO;
//import com.ZamianaRadianow.gra.service.ImageService;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping("/api/images")
//public class ImageController {
//
//    private final ImageService imageService;
//
//    public ImageController(ImageService imageService) {
//        this.imageService = imageService;
//    }
//
//    @PostMapping("/upload")
//    public ResponseEntity<ImageResponseDTO> uploadImage(
//            @RequestParam("file") MultipartFile file,
//            @RequestParam("gameId") Long gameId) throws Exception {
//
//        ImageResponseDTO response = imageService.saveImage(file, gameId);
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
//        ImageResponseDTO dto = imageService.getImageById(id);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_TYPE, dto.getContentType())
//                .body(dto.getData());
//    }
//}
//
