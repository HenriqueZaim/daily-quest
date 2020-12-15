package com.dailyquest.domain.services;

import javax.mail.internet.MimeMessage;

import com.dailyquest.domain.models.Participante;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    
    void sendOrderConfirmationEmail(Participante obj);

    void sendEmail(SimpleMailMessage msg);

    void sendOrderConfirmationHtmlEmail(Participante obj);

    void sendHtmlEmail(MimeMessage msg);
}
