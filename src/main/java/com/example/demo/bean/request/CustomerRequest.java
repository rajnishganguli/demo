package com.example.demo.bean.request;

import jakarta.persistence.Column;

public class CustomerRequest {
    String name;
    String userName;
    String password;
    String address;

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

    public CustomerRequest() {
    }

    public CustomerRequest(String name, String userName, String address) {
        this.name = name;
        this.userName = userName;
        this.address = address;
    }

    public CustomerRequest(String name, String userName, String password, String address) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.address = address;
    }
}
