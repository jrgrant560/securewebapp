package com.securewebapp.auth;

import jakarta.persistence.*;

// User model
@Entity
public class User {

    @Id //primary key
    @GeneratedValue(strategy = GenerationType.AUTO) //automatic generation for the primary key
    private Long id;

    @Column(nullable = false, unique = true) //this field is not nullable and must be unique
    private String username;

    private String password;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}