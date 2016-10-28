package com.byteflair.oauth.server.domain;

import javax.persistence.*;

/**
 * Created by rpr on 22/03/16.
 */

@Entity
@Table(name = "userdetails")
public class UserDetail {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "iduser")
    User user;
    @Column(name = "detailkey", length = 30, nullable = false)
    String key;
    @Column(name = "detailvalue", length = 100, nullable = true)
    String value;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iduserdetail")
    private int id;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
