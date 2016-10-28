/*
 * Copyright (c) 2016 Byteflair
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
