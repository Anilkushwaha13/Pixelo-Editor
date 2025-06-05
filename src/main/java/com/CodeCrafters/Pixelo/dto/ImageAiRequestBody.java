package com.CodeCrafters.Pixelo.dto;

public class ImageAiRequestBody {
    public String imageData;
    public  String message;

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
