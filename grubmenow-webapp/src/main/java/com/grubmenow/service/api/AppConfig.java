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
import com.grubmenow.service.persist.PersistenceHandler;
import com.grubmenow.service.persist.PersistenceHandlerImpl;

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
        return new FacebookAuthentication(facebookAppId, facebookSecretId);
    }

    @Bean
    public StripePaymentProcessor getStripePaymentProcessor()
    {
        String stripeSecretKey = env.getProperty("stripeSecretKey");
        return new StripePaymentProcessor(stripeSecretKey);
    }

    @Bean
    public EmailSender getEmailSender() throws Exception
    {
        String sesAwsAccessKey = env.getProperty("sesAwsAccessKey");
        String sesAwsSecretKey = env.getProperty("sesAwsSecretKey");
        return new EmailSender(sesAwsAccessKey, sesAwsSecretKey, isProduction());
    }

    @Bean
    public PersistenceHandler getPersistenceHandler()
    {
        String databaseUrl = env.getProperty("databaseConnectionUrl");
        String databaseUsername = env.getProperty("databaseUsername");
        String databasePassword = env.getProperty("databasePassword");
        return new PersistenceHandlerImpl(databaseUrl, databaseUsername, databasePassword);
    }

    public boolean isProduction()
    {
        String[] activeProfiles = env.getActiveProfiles();
        if (activeProfiles == null)
        {
            log.info("No profile is treated as production profile");
            return true;
        }
        for (String activeProfile: activeProfiles)
        {
            if ("dev".equals(activeProfile))
            {
                log.info("Any profile dev means we are test profile");
                return false;
            }
        }

        // default for now is production profile
        log.info("No matching profile for now is treated as production profile");
        return true;
    }

} 
