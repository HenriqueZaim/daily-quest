package com.dailyquest.api.config.profiles;

import java.text.ParseException;

import com.dailyquest.domain.services.DBService;
import com.dailyquest.domain.services.EmailService;
import com.dailyquest.domain.services.MockEmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
    private DBService dBService;

	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dBService.instantiateTestDatabase();
		return true;
	}

	@Bean
	public EmailService emailService(){
		return new MockEmailService();
	}

}