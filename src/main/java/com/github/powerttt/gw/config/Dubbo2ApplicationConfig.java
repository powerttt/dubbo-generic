package com.github.powerttt.gw.config;


import org.apache.dubbo.config.ApplicationConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Dubbo2ApplicationConfig {

    @Value("${dubbo.application.name}")
    private String applicationName;

    @Bean
    public ApplicationConfig applicationConfig(){
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(applicationName);
        return applicationConfig;
    }


}
