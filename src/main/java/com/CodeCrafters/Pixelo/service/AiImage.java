package com.CodeCrafters.Pixelo.service;

import com.CodeCrafters.Pixelo.util.Base64Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Base64;

@Service
public class AiImage {

    @Autowired
    com.CodeCrafters.Pixelo.repository.AiImageUpdate aiImageUpdate;

    public  boolean UpdateAiImage(String userName, String base64Image){
        try {
            System.out.println(base64Image);
            String type =base64Image.split(",")[0].split(";")[0].split("/")[1];
            String base64data = base64Image.split(",")[1];
            byte[] bytes =  Base64.getDecoder().decode(base64data);
            boolean bol = aiImageUpdate.updateImage(userName,type,bytes);
            return bol;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public  ArrayList<String> getAiImage(int req){
        ArrayList<BufferedImage> list = aiImageUpdate.getAiImage(req);
        ArrayList<String> base64Image = new ArrayList<>();
        for (BufferedImage img : list){
            String image = Base64Code.getEncodeImage(img,"png");
            base64Image.add(image);
        }

        return base64Image;
    }
}
