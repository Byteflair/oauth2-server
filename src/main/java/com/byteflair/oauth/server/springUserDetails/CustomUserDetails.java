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

import com.byteflair.oauth.server.domain.Role;
import com.byteflair.oauth.server.domain.User;
import com.byteflair.oauth.server.domain.UserDetail;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

/**
 * Created by rpr on 22/03/16.
 */
@Getter
@Setter
@Slf4j
public class CustomUserDetails implements UserDetails, Serializable {

    private String password;
    private String username;

    private List<GrantedAuthority> authorities;
    @Transient
    private boolean accountNonExpired = true;
    @Transient
    private boolean accountNonLocked = true;
    @Transient
    private boolean credentialsNonExpired = true;
    private boolean enabled;
    private String name;
    private int systemId;
    private String phone;
    private String phone1;
    private String phone2;
    private String email;
    private String postalAddress;
    private Map<String, String> details;

    public CustomUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.enabled = user.getUserState().getId() == 1;
        this.authorities = new ArrayList<>();
        this.name = user.getName();
        this.systemId = user.getSystem().getId();
        this.phone = user.getPhone();
        this.phone1 = user.getPhone1();
        this.phone2 = user.getPhone2();
        this.email = user.getEmail();
        this.postalAddress = user.getPostalAddress();
        this.details = new HashMap<>();
    }

    public void addAuthorities(Set<Role> roles) {
        for (Role role : roles) {
            authorities.addAll(AuthorityUtils.createAuthorityList(role.getRolename()));
        }
    }

    public void addDetails(Set<UserDetail> details) {
        for (UserDetail detail : details) {
            this.details.put(detail.getKey(), detail.getValue());
        }
    }
}
