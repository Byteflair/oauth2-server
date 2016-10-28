package com.byteflair.oauth.server.springUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by rpr on 22/03/16.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    CustomUserDetailsTransactionalService customUserDetailsTransaction;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return customUserDetailsTransaction.loadUserByUsername(username);
    }
}
