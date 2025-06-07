package com.CodeCrafters.Pixelo.controller;

import com.CodeCrafters.Pixelo.dto.UserRequest;
import com.CodeCrafters.Pixelo.repository.DatabaseUpdate;
import com.CodeCrafters.Pixelo.service.JWTToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
public class SignUpController {

    @Autowired
    JWTToken tokenChecker;

    @Autowired
    DatabaseUpdate databaseUpdate;


    @PostMapping("/signup")
    public ResponseEntity<?> getRegister(@RequestBody UserRequest request){
        return ResponseEntity.ok()
                .header("Register","Successful")
                .contentType(MediaType.APPLICATION_JSON)
                .body(databaseUpdate.getRegister(request.getUserName(), request.getEmail(), request.getPassword()));
    }

    @PostMapping("/getUpdate")
    public ResponseEntity<?> getUpdate(@RequestParam String email, String name, HttpServletRequest req){

        String authHeader = req.getHeader("Authorization");
        if (authHeader ==null  || !authHeader.startsWith("Bearer ")){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String token = authHeader.substring(7);

        if (tokenChecker.validateToken(email, token)) {
            Boolean registration = databaseUpdate.getUpdate(email, name);
                return ResponseEntity.ok()
                        .header("Change","Successful")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(registration);
            }
        else {
            return ResponseEntity.badRequest()
                    .body(false);
        }

    }
}
