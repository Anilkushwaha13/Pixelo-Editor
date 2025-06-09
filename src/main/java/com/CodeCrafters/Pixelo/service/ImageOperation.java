package com.CodeCrafters.Pixelo.service;

import com.CodeCrafters.Pixelo.exception.PixeloException;
import com.CodeCrafters.Pixelo.util.Base64Code;
import com.CodeCrafters.Pixelo.util.ImageCompressorWithQuality;
import com.CodeCrafters.Pixelo.util.ImageTypeConvertor;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageOperation {

    public  List<String> getCompressedImageWithQuality(List<String> Base64Image, String Quality){
        List<String> result = new ArrayList<>();
        try {

            for (String Base64Images: Base64Image){
                String formateName = Base64Images.split(",")[0].split("/")[1].split(";")[0];
                String base64data = Base64Images.split(",")[1];
                BufferedImage image = Base64Code.getDecodeImage(base64data);
                try {

                    BufferedImage img= ImageCompressorWithQuality.getCompress(image,formateName,Float.parseFloat(Quality));
                    result.add(Base64Code.getEncodeImage(img,formateName));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public  List<String> getConvert(List<String> Base64Image,String Type){
        List<String> result = new ArrayList<>();
        try {

            for (String Base64Images: Base64Image){
                String base64data = Base64Images.split(",")[1];
                BufferedImage image = Base64Code.getDecodeImage(base64data);
                try {
                    BufferedImage img = ImageTypeConvertor.getConvert(image,Type);
                    if (img == null){
                        System.out.println("handle kar"); throw new PixeloException("Convertion failed");}
                    else image=img;

                } catch (Exception e) {
                    System.out.println("Convertor: "+e.getMessage());
                }
                result.add(Base64Code.getEncodeImage(image,Type));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
