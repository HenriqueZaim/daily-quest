package com.dailyquest.domain.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.dailyquest.domain.models.Participante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendOrderConfirmationEmail(Participante obj) {
        SimpleMailMessage sm = prepareSimpleMailMessageFromParticipante(obj);
        sendEmail(sm);
    }

    @Override
    public void sendOrderConfirmationHtmlEmail(Participante obj) {
        try {
            MimeMessage mm = prepareMimeMessageFromParticipante(obj);
            sendHtmlEmail(mm);
        } catch (MessagingException e) {
            sendOrderConfirmationEmail(obj);
        }
        
    }

    protected MimeMessage prepareMimeMessageFromParticipante(Participante obj) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
        mmh.setTo(obj.getParticipante().getUsuario().getEmail());
        mmh.setFrom(sender);
        mmh.setSubject("asease");
        mmh.setSentDate(new Date(System.currentTimeMillis()));
        mmh.setText(htmlFromTemplate(obj), true);
        return mimeMessage;
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromParticipante(Participante obj) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(obj.getParticipante().getUsuario().getEmail());
        sm.setFrom(sender);
        sm.setSubject("Participante loco");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(obj.toString());
        return sm;
    }

    protected String htmlFromTemplate(Participante obj){
        Context context = new Context();
        context.setVariable("participante", obj);
        return templateEngine.process("email/confirmacaoParticipante", context);
    }
}
