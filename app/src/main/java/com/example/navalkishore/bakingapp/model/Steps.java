package com.example.navalkishore.bakingapp.model;

import java.io.Serializable;

public class Steps implements Serializable {
    private Integer id;
    private String shortDescription;

    public Integer getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    private String description;
    private String videoURL;
    private String thumbnailURL;

}
