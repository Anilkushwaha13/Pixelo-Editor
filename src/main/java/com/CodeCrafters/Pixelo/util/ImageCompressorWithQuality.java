package com.CodeCrafters.Pixelo.util;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.spi.IIORegistry;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Iterator;

public class ImageCompressorWithQuality {

    public static BufferedImage getCompress(BufferedImage img , String imageFormate, float quality) {
        return compressImage(img, imageFormate,  quality );
    }

    private static BufferedImage compressImage(BufferedImage img,String imageFormate, float quality) {
        getplugin();
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(imageFormate);
        if (!writers.hasNext()) {
            throw new IllegalStateException("no " + imageFormate + " writer was found");
        }
         ImageWriter writer = writers.next();

        try (ByteArrayOutputStream bimage = new ByteArrayOutputStream();
             ImageOutputStream ios = ImageIO.createImageOutputStream(bimage)) {
            writer.setOutput(ios);
            ImageWriteParam param = writer.getDefaultWriteParam();
//            String[] types = param.getCompressionTypes();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//            param.setCompressionType(types[0]);
            param.setCompressionQuality(quality);


            writer.write(null, new javax.imageio.IIOImage(img, null, null), param);

            ByteArrayInputStream bimg1 = new ByteArrayInputStream(bimage.toByteArray());
            return ImageIO.read(bimg1);

        } catch (Exception e) {
            System.out.println("compressor: "+e.getMessage());
            return null;
        } finally {
            writer.dispose();
        }
      }
    private static void getplugin(){
        ImageIO.scanForPlugins();
        IIORegistry.getDefaultInstance().registerServiceProvider(
                new com.luciad.imageio.webp.WebPImageWriterSpi()
        );
    }
    }
