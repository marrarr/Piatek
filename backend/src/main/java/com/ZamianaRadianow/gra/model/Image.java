//package com.ZamianaRadianow.gra.model;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//
//@Entity
//@Table(name = "images")
//public class Image {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Lob
//    @NotNull
//    @Column(name = "data", nullable = false)
//    private byte[] data;
//
//    @NotBlank
//    @Column(name = "content_type", nullable = false)
//    private String contentType;
//
//    @OneToOne
//    @JoinColumn(name = "game_id", nullable = false, unique = true)
//    private Game game;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public byte[] getData() {
//        return data;
//    }
//
//    public void setData(byte[] data) {
//        this.data = data;
//    }
//
//    public String getContentType() {
//        return contentType;
//    }
//
//    public void setContentType(String contentType) {
//        this.contentType = contentType;
//    }
//
//    public Game getGame() {
//        return game;
//    }
//
//    public void setGame(Game game) {
//        this.game = game;
//    }
//}
//
