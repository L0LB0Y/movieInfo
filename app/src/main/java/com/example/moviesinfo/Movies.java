package com.example.moviesinfo;

import java.util.Date;

public class Movies {
    private Date created;
    private String release_year;
    private String ownerId;
    private String title;
    private String rating;
    private String objectId;
    private String description;
    private java.util.Date updated;

    public java.util.Date getCreated() {
        return created;
    }

    public String getReleaseYear() {
        return release_year;
    }

    public void setReleaseYear(String releaseYear) {
        this.release_year = releaseYear;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.util.Date getUpdated() {
        return updated;
    }
}
