package com.CodeCrafters.Pixelo.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class Base64Code {

    public static String getEncodeImage(BufferedImage image, String formateName){

        try {
            ByteArrayOutputStream bose = new ByteArrayOutputStream();
            ImageIO.write(image,formateName,bose);
            String Image1 = Base64.getEncoder().encodeToString(bose.toByteArray());
            return "data:image/"+formateName+";base64,"+Image1;

        } catch (Exception e) {
            System.out.println("encoder: "+e.getMessage());
            return null;
        }

    }

    public static BufferedImage getDecodeImage(String img) throws Exception{
        byte[] bytes = Base64.getDecoder().decode(img);
        ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
        return ImageIO.read(bin);
    }

}
