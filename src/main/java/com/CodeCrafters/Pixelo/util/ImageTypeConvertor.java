package com.CodeCrafters.Pixelo.util;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.spi.IIORegistry;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class ImageTypeConvertor {

    private static void getplugin(){
        ImageIO.scanForPlugins();
        IIORegistry.getDefaultInstance().registerServiceProvider(
                new com.luciad.imageio.webp.WebPImageWriterSpi()
        );
    }

    public static BufferedImage getConvert(BufferedImage img, String convertFormate) {
        try {
            ByteArrayOutputStream bimge = new ByteArrayOutputStream();
            ImageOutputStream image = ImageIO.createImageOutputStream(bimge);
            ImageIO.write(img , convertFormate,image);

            ByteArrayInputStream bimg2 = new ByteArrayInputStream(bimge.toByteArray());
            return ImageIO.read(bimg2);

        } catch (Exception e) {
            if (convertFormate.equalsIgnoreCase("webp")){
                     getplugin();
                try {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    ImageOutputStream ios = ImageIO.createImageOutputStream(out);

                    Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("webp");
                    if (!writers.hasNext()){
                        System.out.println("No writer was Found");
                        return null;
                    }
                    ImageWriter writer = writers.next();
                    writer.setOutput(ios);
                    writer.write(img);
                    ios.close();
                    writer.dispose();

                    ByteArrayInputStream image = new ByteArrayInputStream(out.toByteArray());
                    return ImageIO.read(image);

                } catch (IOException ex) {
                    System.out.println("error in webp: "+ex.getMessage());
                    return null;
                }

            }
            else {
            System.out.println("error in convertor: "+e.getMessage());
            return null;
        }

    }
}
}
