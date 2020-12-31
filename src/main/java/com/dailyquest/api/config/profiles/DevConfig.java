package com.dailyquest.api.config.profiles;


import com.dailyquest.domain.services.EmailService;
import com.dailyquest.domain.services.SmtpEmailService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"dev", "prod"})
public class DevConfig {

    @Bean
	public EmailService emailService(){
		return new SmtpEmailService();
	}

}