package com.CodeCrafters.Pixelo.service;

import com.CodeCrafters.Pixelo.util.Base64Code;
import com.CodeCrafters.Pixelo.util.RembgJava;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

@Service
public class BgRemover {
    public String getBgRemoved(String image){
        try {
            String base64data = image.split(",")[1];
            BufferedImage bufferedImage = Base64Code.getDecodeImage(base64data);
            BufferedImage newImg = RembgJava.getBgRemoved(bufferedImage);
            return Base64Code.getEncodeImage(newImg,"png");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
