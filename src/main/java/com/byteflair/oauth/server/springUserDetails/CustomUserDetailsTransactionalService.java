package com.byteflair.oauth.server.springUserDetails;

import com.byteflair.oauth.server.domain.Group;
import com.byteflair.oauth.server.domain.User;
import com.byteflair.oauth.server.domain.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by rpr on 22/03/16.
 */

@Service
@Transactional(readOnly = true)
@Slf4j
public class CustomUserDetailsTransactionalService {

    @Autowired
    UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //retreive user from database
        User user = userRepository.findByUsername(username);
        if (user == null) {
            LOGGER.warn("User {} does not exist in database.", username);
            throw new UsernameNotFoundException(String.format("User %s does not exist in database.", username));
        }
        LOGGER.info("Retrieved user {}", user);

        //create a CustomUserDetails
        CustomUserDetails userDetails = new CustomUserDetails(user);

        //add roles for user
        userDetails.addAuthorities(user.getRoles());

        //add roles for groups
        for (Group group : user.getGroups()) {
            userDetails.addAuthorities(group.getRoles());
        }

        //add UserDetails from database
        userDetails.addDetails(user.getUserDetails());

        return userDetails;
    }
}
