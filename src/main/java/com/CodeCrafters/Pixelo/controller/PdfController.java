package com.CodeCrafters.Pixelo.controller;

import com.CodeCrafters.Pixelo.dto.ImageRequest;
import com.CodeCrafters.Pixelo.util.PdfMaker;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    @PostMapping("/make-pdf")
    public ResponseEntity<?> generatePdf(@RequestBody ImageRequest request) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=test.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(PdfMaker.makePdf(request.getImages()));
    }

}
