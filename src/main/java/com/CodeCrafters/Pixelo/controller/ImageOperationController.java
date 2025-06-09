package com.CodeCrafters.Pixelo.controller;

import com.CodeCrafters.Pixelo.dto.ImageDownSaveRequest;
import com.CodeCrafters.Pixelo.dto.ImageRequestwithQualityType;
import com.CodeCrafters.Pixelo.service.BgRemover;
import com.CodeCrafters.Pixelo.service.ImageOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/image")
public class ImageOperationController {

    @Autowired
    ImageOperation imageOperation;

    @Autowired
    BgRemover bgRemover;

    @PostMapping("/get-compressed-quality")
    public ResponseEntity<Map<String, List<String>>> compressImageWithQuality(@RequestBody ImageRequestwithQualityType request){

        Map<String,List<String>> response = new HashMap<>();
        response.put("Images",imageOperation.getCompressedImageWithQuality(request.getImages(),request.getQualityOrType()));

        return ResponseEntity.ok()
                .header("X-Total-Images", String.valueOf(0))
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping("/get-convert")
    public ResponseEntity<?> convertImage(@RequestBody ImageRequestwithQualityType request){
        Map<String,List<String>> response = new HashMap<>();
        response.put("Images",imageOperation.getConvert(request.getImages(),request.getQualityOrType()));

        return ResponseEntity.ok()
                .header("X-Total-Images", String.valueOf(0))
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping("/get-bgRemoved")
    public ResponseEntity<?> BgRemoveImage(@RequestBody ImageDownSaveRequest request){
        Map<String,String> response = new HashMap<>();
        response.put("Image",bgRemover.getBgRemoved(request.getImage()));

        return ResponseEntity.ok()
                .header("X-Total-Images", String.valueOf(0))
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

}
