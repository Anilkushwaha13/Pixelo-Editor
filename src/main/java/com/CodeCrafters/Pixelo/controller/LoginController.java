package com.CodeCrafters.Pixelo.controller;

import com.CodeCrafters.Pixelo.dto.UserLogin;
import com.CodeCrafters.Pixelo.service.LoginLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    LoginLogic loginLogic;

    @PostMapping("/get-login")
    public ResponseEntity<?> getLogin(@RequestBody UserLogin request){
        Map<String,String> loginList = loginLogic.getLoginToken(request.getEmail(),request.getPassword());
        if (loginList != null && !loginList.containsKey("password")){
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(loginList);
        } else {
            loginList = null;
            return ResponseEntity.accepted().body(false);
        }
    }
}
