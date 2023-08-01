package com.pvs.perfectresume.service;

import com.pvs.perfectresume.model.EmailDetails;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    String sendEmail(EmailDetails emailDetails);

    String sendEmailWithAttachment(EmailDetails emailDetails);
}
