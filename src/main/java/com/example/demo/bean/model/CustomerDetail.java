package com.example.demo.bean.model;

import jakarta.persistence.*;

@Entity(name = "CUSTOMER_DTL")
public class CustomerDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    Long id;

    @Column(name="NAME")
    String name;

    @Column(name="USER_NAME", unique = true)
    String userName;

    @Column(name="PASSWORD")
    String password;

    @Column(name="ADDRESS")
    String address;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CustomerDetail() {
    }

    public CustomerDetail(Long id, String name, String userName, String address) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.address = address;
    }

    public CustomerDetail(String name, String userName, String password, String address) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.address = address;
    }

    public CustomerDetail(Long id, String name, String userName, String password, String address) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.address = address;
    }
}
