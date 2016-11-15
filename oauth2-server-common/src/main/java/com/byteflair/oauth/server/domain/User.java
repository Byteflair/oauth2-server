/*
 * Copyright (c) 2016 Byteflair
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.byteflair.oauth.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {
   /*
    * CREATE TABLE `users` (
    *   `iduser` int(11) NOT NULL AUTO_INCREMENT,
    *   `username` varchar(45) NOT NULL,
    *   `password` varchar(100) NOT NULL,
    *   `idsystem` int(11) NOT NULL,
    *   `name` varchar(100) NOT NULL,
    *   `phone` varchar(45) DEFAULT NULL,
    *   `phone1` varchar(45) DEFAULT NULL,
    *   `phone2` varchar(45) DEFAULT NULL,
    *   `email` varchar(100) NOT NULL,
    *   `postaladdress` varchar(512) DEFAULT NULL,
    *   `iduserstate` int(11) NOT NULL,
    *   PRIMARY KEY (`iduser`),
    *   UNIQUE KEY `login_UNIQUE` (`username`),
    *   KEY `systemstousers_idx` (`idsystem`),
    *   KEY `userstatestousers_idx` (`iduserstate`),
    *   CONSTRAINT `userstatestousers` FOREIGN KEY (`iduserstate`) REFERENCES `userstates` (`iduserstate`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    *   CONSTRAINT `systemstousers` FOREIGN KEY (`idsystem`) REFERENCES `systems` (`idsystem`) ON DELETE NO ACTION ON UPDATE NO ACTION
    * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
    */

    @Column(name = "username", length = 45, nullable = false, unique = true)
    String username;

    @Column(name = "password", length = 100)
    @JsonIgnore
    String password;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idsystem")
    System system;

    @Column(name = "name", length = 100, nullable = false)
    String name;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    String email;

    @Column(name = "phone", length = 45)
    String phone;

    @Column(name = "phone1", length = 45)
    String phone1;

    @Column(name = "phone2", length = 45)
    String phone2;

    @Column(name = "postaladdress", length = 512)
    String postalAddress;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "iduserstate")
    @RestResource(rel = "user-state", path = "user-state")
    UserState userState;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "groupmembers",
        joinColumns = @JoinColumn(name = "iduser", referencedColumnName = "iduser"),
        inverseJoinColumns = @JoinColumn(name = "idgroup", referencedColumnName = "idgroup")
    )
    Set<Group> groups;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rolesperuser",
        joinColumns = @JoinColumn(name = "iduser", referencedColumnName = "iduser"),
        inverseJoinColumns = @JoinColumn(name = "idrole", referencedColumnName = "idrole")
    )
    Set<Role> roles;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "iduser")
    Set<UserDetail> userDetails;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iduser")
    private Integer id;
}
