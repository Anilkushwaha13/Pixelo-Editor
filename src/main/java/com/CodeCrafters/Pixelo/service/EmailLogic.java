package com.CodeCrafters.Pixelo.service;

import com.CodeCrafters.Pixelo.util.Otpgenrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailLogic {

    @Autowired
     EmailSender emailSender;
    public int sendOtp(String toEmail){
        int otp = Otpgenrator.getotp();
        System.out.println("otp:"+otp);
        String subject = "OTP for ForgetPassword";
        String body = String.format(
                "Your One-Time Password (OTP) for verification is: %s\n\n" +
                        "This OTP is valid for 5 minutes.\n\n" +
                        "⚠️ Please do not share this code with anyone. For your security.\n\n" +
                        "Thank you for using Pixelo.",
                otp);
        try {
            emailSender.sendEmail(toEmail, subject, body);
            return otp;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;

        }

    }
}
