package com.CodeCrafters.Pixelo.controller;

import com.CodeCrafters.Pixelo.dto.ReviewBody;
import com.CodeCrafters.Pixelo.service.JWTToken;
import com.CodeCrafters.Pixelo.service.ReviewLogic;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    JWTToken tokenChecker;

    @Autowired
    ReviewLogic reviewLogic;

    @PostMapping("/save")
    public ResponseEntity<?> Review(@RequestBody ReviewBody request, @RequestParam String email, HttpServletRequest req) {
        String authHeader = req.getHeader("Authorization");
        if (authHeader ==null  || !authHeader.startsWith("Bearer ")){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String token = authHeader.substring(7);


        System.out.println(request.getRating());
        System.out.println(email);
        boolean bol = false;
        if (tokenChecker.validateToken( email, token)) {
            bol = reviewLogic.review(email,request.getRating(),request.getSummary(),request.getLocation());
            return ResponseEntity.ok()
                    .body(bol);

        }else {
            return ResponseEntity.ok()
                    .body(bol);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAiImage(){

            return ResponseEntity.ok()
                    .body(reviewLogic.getReview());
    }

    @DeleteMapping("/delete")
    public  ResponseEntity<?> deleteDraft(@RequestParam String email,HttpServletRequest req){
        String authHeader = req.getHeader("Authorization");
        if (authHeader ==null  || !authHeader.startsWith("Bearer ")){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String token = authHeader.substring(7);
        if (tokenChecker.validateToken(email,token )) {
            return ResponseEntity.ok()
                    .body(reviewLogic.deleteReview(email));
        }
        else return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }
}
