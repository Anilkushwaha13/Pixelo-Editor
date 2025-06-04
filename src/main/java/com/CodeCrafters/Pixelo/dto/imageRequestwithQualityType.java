package com.CodeCrafters.Pixelo.dto;

import java.util.ArrayList;
import java.util.List;

public class imageRequestwithQualityType {
        private List<String> images = new ArrayList<>();
        private String qualityOrType;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getQualityOrType() {
        return qualityOrType;
    }

    public void setQualityOrType(String qualityOrType) {
        this.qualityOrType = qualityOrType;
    }
}
