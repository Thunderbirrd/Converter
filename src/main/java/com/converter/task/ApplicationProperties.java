package com.converter.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Configuration
public class ApplicationProperties {

    @Autowired
    private Environment env;

    private static Environment environment;

    @PostConstruct
    public void init(){
        environment = env;
    }

    public String getProperty(String key){
        return environment.getProperty(key);
    }

}
