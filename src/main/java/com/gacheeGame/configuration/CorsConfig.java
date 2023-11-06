package com.gacheeGame.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer
{
    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        //전체 resource를 허용할 경우 registry.addMapping("/**")...
        registry.addMapping("/**")   //mapping할 resource를 지정합니다.
            .allowedOrigins("http://localhost:3000", "http://localhost:8085")
            .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE"); //CORS를 허용할 origin을 지정합니다.
    }
}
