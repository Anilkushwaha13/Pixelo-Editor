package com.CodeCrafters.Pixelo.controller;

import com.CodeCrafters.Pixelo.repository.DatabaseUpdate;
import com.CodeCrafters.Pixelo.service.EmailLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/otp")
public class OtpController {

    @Autowired
    EmailLogic emailLogic;

    @Autowired
    DatabaseUpdate databaseUpdate;

    @GetMapping("/send")
    public ResponseEntity<?> sendOtp(@RequestParam String email){
        int num = emailLogic.sendOtp(email);
        if(num==0){
            return ResponseEntity.ok()
                    .body(false);
        }
        else {
            return ResponseEntity.ok()
                    .body(num);
        }

    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam String email){
        return ResponseEntity.ok()
                .body(databaseUpdate.getUser(email));
    }

}
