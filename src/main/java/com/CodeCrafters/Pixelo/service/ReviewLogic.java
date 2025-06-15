package com.CodeCrafters.Pixelo.service;

import com.CodeCrafters.Pixelo.repository.ReviewUpdate;
import com.CodeCrafters.Pixelo.util.Base64Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewLogic {
    @Autowired
    ReviewUpdate reviewUpdate;

    public  boolean review(String email, double rating, String summ,String city){
        try {
            boolean bol = reviewUpdate.Review(email,rating,summ,city);
            return bol;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public List<Map<String,String>>getReview(){
       return reviewUpdate.getReview();
    }
    public  boolean deleteReview(String email) {
        return reviewUpdate.reviewDelete(email);

    }
}
