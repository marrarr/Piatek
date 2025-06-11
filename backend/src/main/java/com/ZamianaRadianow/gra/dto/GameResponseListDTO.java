package com.ZamianaRadianow.gra.dto;

import java.sql.Timestamp;
import java.util.Set;

public class GameResponseListDTO {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;
    private String title;
    private double averageRating;

    private ImageResponseDTO image = null;

    public ImageResponseDTO getImage() {
        return image;
    }

    public void setImage(ImageResponseDTO image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
}
