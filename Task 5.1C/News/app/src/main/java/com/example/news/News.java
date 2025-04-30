package com.example.news;

import java.io.Serializable;

public class News implements Serializable {
    private final int image;
    private final String title;
    private final String description;

    public News(int image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

}