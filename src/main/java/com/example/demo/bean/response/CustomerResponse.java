package com.example.demo.bean.response;

import com.example.demo.bean.model.CustomerDetail;

import java.io.Serializable;

public class CustomerResponse implements Serializable {
    Long id;
    String name;
    String userName;
    String address;

    public CustomerResponse() {
    }

    public CustomerResponse(Long id, String name, String userName, String address) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.address = address;
    }

    public CustomerResponse(CustomerDetail customerDetail) {
        this.id = customerDetail.getId();
        this.name = customerDetail.getName();
        this.userName = customerDetail.getUserName();
        this.address = customerDetail.getAddress();
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
