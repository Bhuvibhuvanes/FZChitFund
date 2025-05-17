package fz.chitfund.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordResetEmail(String toEmail, String resetToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, use this token: " + resetToken + 
                      "\n\nThis token will expire in 1 hour." +
                      "\n\nIf you did not request a password reset, please ignore this email.");
        
        mailSender.send(message);
    }

    public void sendWelcomeEmail(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Welcome to FrenzoChitFund");
        message.setText("Thank you for registering with FrenzoChitFund." +
                      "\n\nYou can now log in to your account and start using our services.");
        
        mailSender.send(message);
    }
}
