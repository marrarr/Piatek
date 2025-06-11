package com.ZamianaRadianow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;
import java.util.Set;

public class GameDTO {

    @NotBlank
    @Size(max = 100)
    private String title;

    private Timestamp releaseDate;

    @NotBlank
    @Size(max = 100)
    private String developer;

    @NotBlank
    @Size(max = 100)
    private String publisher;

    @Size(max = 1000)
    private String description;

    private Set<Long> genreIds;
    private Set<Long> platformIds;





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

    public Set<Long> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(Set<Long> genreIds) {
        this.genreIds = genreIds;
    }

    public Set<Long> getPlatformIds() {
        return platformIds;
    }

    public void setPlatformIds(Set<Long> platformIds) {
        this.platformIds = platformIds;
    }
}
