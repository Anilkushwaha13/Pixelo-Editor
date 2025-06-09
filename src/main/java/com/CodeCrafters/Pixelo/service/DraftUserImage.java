package com.CodeCrafters.Pixelo.service;

import com.CodeCrafters.Pixelo.repository.DraftImageUpdate;
import com.CodeCrafters.Pixelo.util.Base64Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class DraftUserImage {

    @Autowired
    DraftImageUpdate draftImageUpdate;

    public  boolean draft(String email, String base64Image){
        try {
            System.out.println(base64Image);
            String type =base64Image.split(",")[0].split(";")[0].split("/")[1];
            String base64data = base64Image.split(",")[1];
            System.out.println("type"+type);
            byte[] bytes =  Base64.getDecoder().decode(base64data);
            System.out.println(bytes);
            boolean bol = draftImageUpdate.draftImage(email,type,bytes);
            return bol;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public  Map<Integer, String> getDraft(String email){
        Map<Integer, BufferedImage> list = draftImageUpdate.getDraftImage(email);
        Map<Integer,String> encodedImage = new HashMap<>();

        for ( Map.Entry<Integer, BufferedImage> entry : list.entrySet()){
            String image = Base64Code.getEncodeImage(entry.getValue(),"png");
            encodedImage.put(entry.getKey(),image);
        }

        return encodedImage;
    }
    public  boolean deleteDraft(String email, int id) {
        return draftImageUpdate.deleteDraftImage(email,id);

    }

}
