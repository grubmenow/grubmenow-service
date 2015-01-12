package com.grubmenow.service.api;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.grubmenow.service.auth.FacebookAuthentication;
import com.grubmenow.service.notif.email.EmailSender;
import com.grubmenow.service.pay.StripePaymentProcessor;

@Configuration 
@ComponentScan("com.grubmenow") 
@EnableWebMvc
@PropertySource({ "classpath:config/application-${spring.profiles.active:production}.properties" })
@CommonsLog
public class AppConfig
{
    @Autowired
    private Environment env;

    @Bean
    public FacebookAuthentication getFacebookAuthentication()
    {
        String facebookAppId = env.getProperty("facebookAppId");
        String facebookSecretId = env.getProperty("facebookSecretId");
        log.info("Loading facebook App Id ["+ facebookAppId +"]");
        FacebookAuthentication facebookAuthentication = new FacebookAuthentication(facebookAppId, facebookSecretId);
        return facebookAuthentication;
    }

    @Bean
    public StripePaymentProcessor getStripePaymentProcessor()
    {
        String stripeSecretKey = env.getProperty("stripeSecretKey");
        StripePaymentProcessor stripePaymentProcessor = new StripePaymentProcessor(stripeSecretKey);
        return stripePaymentProcessor;
    }

    @Bean
    public EmailSender getEmailSender() throws Exception
    {
        String sesAwsAccessKey = env.getProperty("sesAwsAccessKey");
        String sesAwsSecretKey = env.getProperty("sesAwsSecretKey");
        EmailSender emailSender = new EmailSender(sesAwsAccessKey, sesAwsSecretKey);
        return emailSender;
    }
} 
