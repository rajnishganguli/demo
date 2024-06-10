package com.example.demo.bean.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity(name = "ADMIN_DTL")
public class AdminDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    Long id;

    @Column(name = "NAME")
    String name;

    @Column(name = "USER_NAME", unique = true)
    String userName;

    @Column(name = "PASSWORD")
    String password;

    public AdminDetail() {
    }

    public AdminDetail(Long id) {
        this.id = id;
    }

    public AdminDetail(String name, String userName, String password) {
        this.name = name;
        this.userName = userName;
        this.password = password;
    }

    public AdminDetail(Long id, String name, String userName, String password) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
