package com.CodeCrafters.Pixelo.controller;

import com.CodeCrafters.Pixelo.dto.ImageDownSaveRequest;
import com.CodeCrafters.Pixelo.service.JWTToken;
import com.CodeCrafters.Pixelo.service.AiImage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/download")
public class DownloadController {
    @Autowired
    JWTToken tokenChecker;

    @Autowired
    AiImage aiImage;

    @GetMapping("/download")
    public ResponseEntity<?> getDownload(@RequestParam String email, HttpServletRequest req) {
        String authHeader = req.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String token = authHeader.substring(7);

        if (tokenChecker.validateToken(email, token)) {
            return ResponseEntity.ok()
                    .body(true);
        } else {
            return ResponseEntity.badRequest()
                    .body(false);
        }
    }

    @GetMapping("/ai-download")
    public ResponseEntity<?> getDownloadAndUpdate(@RequestBody ImageDownSaveRequest request, @RequestParam String email, HttpServletRequest req) {
        String authHeader = req.getHeader("Authorization");
        if (authHeader ==null  || !authHeader.startsWith("Bearer ")){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String token = authHeader.substring(7);

        if (tokenChecker.validateToken(email, token)) {
             aiImage.UpdateAiImage(email,request.getImage());
            return ResponseEntity.ok()
                    .body(true);
        }
        else {
            return ResponseEntity.badRequest()
                    .body(false);
        }
    }

    @GetMapping("/ai-image")
    public ResponseEntity<?> getAiImage(@RequestParam int req) {
        return ResponseEntity.ok()
                .body(aiImage.getAiImage(req));
    }

}
