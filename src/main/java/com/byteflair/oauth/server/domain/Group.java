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
