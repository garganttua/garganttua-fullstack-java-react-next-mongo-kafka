package com.garganttua.noghost.backend;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class BackendWebMvcConfigurer implements WebMvcConfigurer {

	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/{spring:[^\\.]*}")
                .setViewName("forward:/index.html");
    }
}
