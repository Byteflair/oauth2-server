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

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "groups")
public class Group {

   /*
    * CREATE TABLE `groups` (
    *  `idgroup` int(11) NOT NULL AUTO_INCREMENT,
    *  `groupname` varchar(45) NOT NULL,
    *  PRIMARY KEY (`idgroup`)
    * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
    */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idgroup")
    int id;

    @Column(name = "groupname", length = 45, nullable = false)
    String groupname;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rolespergroup",
        joinColumns = @JoinColumn(name = "idgroup", referencedColumnName = "idgroup", unique = false),
        inverseJoinColumns = @JoinColumn(name = "idrole", referencedColumnName = "idrole", unique = false)
    )
    Set<Role> roles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
