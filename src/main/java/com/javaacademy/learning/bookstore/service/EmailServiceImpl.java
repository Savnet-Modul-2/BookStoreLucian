package com.javaacademy.learning.bookstore.service;
import com.javaacademy.learning.bookstore.emailsender.EmailDetails;
import com.javaacademy.learning.bookstore.emailsender.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}") private String sender;
    public String sendSimpleMail(EmailDetails details){
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            mailSender.send(mailMessage);
            return "mail sent successfully";
        }
        catch(Exception e){
            return "error while sending mail";
        }
    }

    @Override
    public String sendMailWithAttachment(EmailDetails details) {
        return "";
    }

}
