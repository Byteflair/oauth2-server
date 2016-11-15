package com.byteflair.oauth.server;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by calata on 15/11/16.
 */
@Configuration
@Import(value = {
    OAuth2AuthorizationConfig.class,
    SecurityConfig.class,
    MvcConfig.class
})
@ComponentScan
public class OauthServerConfig {
}
