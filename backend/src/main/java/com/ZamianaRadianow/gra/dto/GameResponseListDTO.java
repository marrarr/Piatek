package com.ZamianaRadianow.gra.dto;

import java.sql.Timestamp;
import java.util.Set;

public class GameResponseListDTO {
    private String title;
    private double averageRating;


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
