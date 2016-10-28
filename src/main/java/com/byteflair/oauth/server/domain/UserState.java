package com.byteflair.oauth.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userstates")
public class UserState {
   /*
    *  CREATE TABLE `userstates` (
    *  `iduserstate` int(11) NOT NULL,
    *  `description` varchar(45) NOT NULL,
    *  PRIMARY KEY (`iduserstate`)
    *) ENGINE=InnoDB DEFAULT CHARSET=utf8;
    */

    @Id
    @Column(name = "iduserstate", nullable = false)
    int id;

    @Column(name = "description", length = 45, nullable = false)
    String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
