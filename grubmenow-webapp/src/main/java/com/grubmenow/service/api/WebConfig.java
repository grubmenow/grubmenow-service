package com.grubmenow.service.api;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//registry.addResourceHandler("/dashboard/**")
		//	.addResourceLocations("classpath:/dashboard/");
		
		registry.addResourceHandler("/dashboard/**")
			.addResourceLocations("/dashboard/");
	}

}