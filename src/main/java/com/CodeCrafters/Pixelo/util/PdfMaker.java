package com.CodeCrafters.Pixelo.util;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.List;

public class PdfMaker {

    public static byte[] makePdf(List<String> base64Image){
        return getPdf(base64Image);
    }

    private static byte[] getPdf(List<String> base64Image){
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            for (String base64Images : base64Image) {
                if (base64Images.contains(",")) {
                    base64Images = base64Images.split(",")[1];
                }

                byte[] imageBytes = Base64.getDecoder().decode(base64Images);
                ImageData image = ImageDataFactory.create(imageBytes);

                Image img = new Image(image);
                img.setAutoScaleHeight(true);
                img.setAutoScaleWidth(true);

                document.add(img);
            }
            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
