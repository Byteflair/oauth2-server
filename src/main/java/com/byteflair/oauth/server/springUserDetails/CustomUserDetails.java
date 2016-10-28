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
