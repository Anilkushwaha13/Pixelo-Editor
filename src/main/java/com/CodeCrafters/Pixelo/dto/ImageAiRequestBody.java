package com.CodeCrafters.Pixelo.dto;

public class ImageAiRequestBody {
    public String image;
    public  String message;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
