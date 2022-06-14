package com.example.demo.config;

import com.example.demo.util.DateTimeUtils;
import com.example.demo.util.ValidateUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public ValidateUtils validateUtils(){
        return new ValidateUtils();
    }

    @Bean
    public DateTimeUtils dateTimeUtils(){
        return new DateTimeUtils();
    }
}
