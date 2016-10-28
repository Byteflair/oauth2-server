package com.byteflair.oauth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by rpr on 12/12/15.
 */

@SpringBootApplication
@SessionAttributes("authorizationRequest")
@EnableResourceServer
public class Oauth2ServerApp extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2ServerApp.class, args);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }
}