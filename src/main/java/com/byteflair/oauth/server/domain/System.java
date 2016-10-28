package com.byteflair.oauth.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "systems")
public class System {
   
   /* 
    * CREATE TABLE `systems` (
    *  `idsystem` int(11) NOT NULL,
    *  `name` varchar(45) NOT NULL,
    *  PRIMARY KEY (`idsystem`)
    * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
    */

    @Id
    @Column(name = "idsystem", nullable = false)
    int id;

    @Column(name = "name", length = 45, nullable = false)
    String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
