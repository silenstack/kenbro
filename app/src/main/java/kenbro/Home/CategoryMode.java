package com.example.kenbro.Home;

public class CategoryMode {

    String type = null;
    String image = null;

    public CategoryMode(String type, String image) {
        this.type = type;
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String toString() {
        return type;
    }
}

