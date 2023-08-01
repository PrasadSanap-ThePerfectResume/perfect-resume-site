package com.pvs.perfectresume.service.serviceImpl;

import com.pvs.perfectresume.constants.AppConstants;
import com.pvs.perfectresume.model.EmailDetails;
import com.pvs.perfectresume.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendEmail(EmailDetails emailDetails) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setText(emailDetails.getEmailBody());
            mailMessage.setSubject(emailDetails.getSubject());
            javaMailSender.send(mailMessage);
            return AppConstants.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AppConstants.FAILED;
    }

    @Override
    public String sendEmailWithAttachment(EmailDetails emailDetails) {
        return null;
    }
}
