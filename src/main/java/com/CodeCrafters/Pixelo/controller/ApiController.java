package com.CodeCrafters.Pixelo.controller;

import com.CodeCrafters.Pixelo.apiCaller.ImageReaderAi;
import com.CodeCrafters.Pixelo.apiCaller.TextToImageAi;
import com.CodeCrafters.Pixelo.dto.ImageAiRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    TextToImageAi textToImageAi;

    @Autowired
    ImageReaderAi imageReaderAi;

    @GetMapping("/getImage")
    public ResponseEntity<?> getAiImage(@RequestParam String prompt){
        return ResponseEntity.ok()
                .header("X-Total-Images", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .body(textToImageAi.getImage2(prompt));
    }

    @PostMapping("/imageReader")
    public ResponseEntity<?> getImageData(@RequestBody ImageAiRequestBody request){
        Map<String,String> reponse = new HashMap<>();
        reponse.put("data",String.valueOf(imageReaderAi.getImageData(request.getImageData(), request.getMessage())));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(reponse);
    }


}
