package com.CodeCrafters.Pixelo.service;

import com.CodeCrafters.Pixelo.config.EmailConfig;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {
    @Autowired
    private EmailConfig emailConfig;

    @Value("${spring.mail.mymail}")
    private String mymail ;

    public  void sendEmail(String toEmail, String subject, String body ) throws Exception{
        Message message = new MimeMessage(emailConfig.getMailSession());
        message.setFrom(new InternetAddress(mymail));
        message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(toEmail));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
        System.out.println("Email was sent successfully: "+ toEmail);
    }

}
