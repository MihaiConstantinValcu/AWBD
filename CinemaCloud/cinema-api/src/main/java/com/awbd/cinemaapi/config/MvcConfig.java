package com.awbd.cinemaapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.Properties;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Bean(name = "simpleMappingExceptionResolver")
    public SimpleMappingExceptionResolver getSimpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();

        r.setDefaultErrorView("exceptions/defaultException");
        r.setExceptionAttribute("ex");

        Properties mappings = new Properties();
        mappings.setProperty("NumberFormatException", "numberFormatException");
        r.setExceptionMappings(mappings);

        Properties statusCodes = new Properties();
        statusCodes.setProperty("NumberFormatException", "400");
        r.setStatusCodes(statusCodes);

        return r;
    }
}
