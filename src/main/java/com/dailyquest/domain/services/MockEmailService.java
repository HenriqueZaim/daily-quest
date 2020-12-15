package com.dailyquest.domain.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService {

    private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        LOG.info("E-mail enviado");
        LOG.info(msg.toString());
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        LOG.info("E-mail enviadohtml");
        LOG.info(msg.toString());
    }
    
}
