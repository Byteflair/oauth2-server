package com.byteflair.oauth.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;

/**
 * Created by calata on 10/11/16.
 */
@Controller
public class ClientController {

    private static final Integer ACCESS_TOKEN_VALIDITY = 900;
    private static final Integer REFRESH_TOKEN_VALIDITY = 43200;

    @Autowired
    @Qualifier("jdbcClientDetailsService")
    JdbcClientDetailsService jdbcClientDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(method = RequestMethod.POST, value = "/client")
    public void createNewClient(@RequestBody BaseClientDetails clientDetails) {

        Assert.notNull(clientDetails);
        Assert.hasText(clientDetails.getClientId(), "client_id must be informed");
        Assert.hasText(clientDetails.getClientSecret(), "client_secret must be informed");
        Assert.isTrue(!clientDetails.getScope().isEmpty(), "at least one scope must be informed");

        if (clientDetails.getAuthorities() == null || clientDetails.getAuthorities().isEmpty()) {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_TRUSTED_CLIENT");
            clientDetails.setAuthorities(Arrays.asList(authority));
        }

        if (clientDetails.getAuthorizedGrantTypes() == null || clientDetails.getAuthorizedGrantTypes().isEmpty()) {
            clientDetails.setAuthorizedGrantTypes(Arrays.asList("client_credentials"));
        }

        if (clientDetails.getAccessTokenValiditySeconds() == null) {
            clientDetails.setAccessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY);
        }

        if (clientDetails.getRefreshTokenValiditySeconds() == null) {
            clientDetails.setRefreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY);
        }

        jdbcClientDetailsService.setPasswordEncoder(passwordEncoder);
        jdbcClientDetailsService.addClientDetails(clientDetails); // TODO solucionar ViewResolver
    }


    /*@RequestMapping(method = RequestMethod.GET, value = "/client/{id}")
    public BaseClientDetails getClientDetails(@PathVariable(value = "id") String id){

        BaseClientDetails clientDetails = null;
        Assert.hasText(id, "id must be informed");

        clientDetails = (BaseClientDetails) jdbcClientDetailsService.loadClientByClientId(id);
        clientDetails.setClientSecret("*");

        return clientDetails;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/client")
    public List<BaseClientDetails> getClientDetails(){
        List<BaseClientDetails> baseClientDetails = new ArrayList<>();
        List<ClientDetails> clientDetailsList = jdbcClientDetailsService.listClientDetails();

        for (ClientDetails clientDetails: clientDetailsList){
            BaseClientDetails baseClient = (BaseClientDetails) clientDetails;
            baseClient.setClientSecret("*");
            baseClientDetails.add(baseClient);
        }

        return baseClientDetails;

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/client/{id}")
    public void deleteClient(@PathVariable(value = "id") String id) {

        jdbcClientDetailsService.removeClientDetails(id);
    }*/
}
