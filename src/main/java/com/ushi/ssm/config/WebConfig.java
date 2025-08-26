package com.ushi.ssm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**")
        .allowedOrigins(
            "http://localhost:3000",
            "http://127.0.0.1:3000"
        )
        .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
        .allowCredentials(true);
  }
}
