package com.ZamianaRadianow.gra.dto;

import java.sql.Timestamp;
import java.util.Set;

public class GameResponseDetailsDTO {

    private Long id;
    private String title;


    private double averageRating;
    private Timestamp releaseDate;
    private String developer;
    private String publisher;
    private String description;
    private Set<GenreResponseDTO> genres;
    private Set<PlatformResponseDTO> platforms;

//    private ImageResponseDTO image = null;
//
//    public ImageResponseDTO getImage() {
//        return image;
//    }
//
//    public void setImage(ImageResponseDTO image) {
//        this.image = image;
//    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Timestamp releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<GenreResponseDTO> getGenres() {
        return genres;

    }

    public void setGenres(Set<GenreResponseDTO> genres) {
        this.genres = genres;
    }

    public Set<PlatformResponseDTO> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(Set<PlatformResponseDTO> platforms) {
        this.platforms = platforms;
    }


    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

}


