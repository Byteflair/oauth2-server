package com.byteflair.oauth.server.domain;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

   /*
    * CREATE TABLE `roles` (
    *  `idrole` int(11) NOT NULL AUTO_INCREMENT,
    *  `rolename` varchar(45) NOT NULL,
    *  PRIMARY KEY (`idrole`)
    * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
    */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrole")
    int id;

    @Column(name = "rolename", length = 45, nullable = false)
    String rolename;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}
