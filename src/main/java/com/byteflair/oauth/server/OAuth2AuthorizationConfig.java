package com.byteflair.oauth.server;

import com.byteflair.oauth.server.springUserDetails.CustomUserDetailsJwtTokenConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.security.KeyPair;

/**
 * Created by rpr on 24/07/16.
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${keystore.path}")
    private String keystorePath;
    @Value("${keystore.password}")
    private String keystorePassword;
    @Value("${keystore.key.alias}")
    private String keystoreKeyAlias;
    @Value("${keystore.key.password}")
    private String keystoreKeyPassword;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
            .passwordEncoder(passwordEncoder())
            .tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                 .accessTokenConverter(customUserDetailsJwtTokenConverter());
    }

    @Bean
    public CustomUserDetailsJwtTokenConverter customUserDetailsJwtTokenConverter() throws FileNotFoundException {
        CustomUserDetailsJwtTokenConverter converter = new CustomUserDetailsJwtTokenConverter();
        KeyPair keyPair = new KeyStoreKeyFactory(new FileSystemResource(ResourceUtils.getFile(keystorePath)),
                                                 keystorePassword.toCharArray())
            .getKeyPair(keystoreKeyAlias, keystoreKeyPassword.toCharArray());
        converter.setKeyPair(keyPair);
        return converter;
    }
}
