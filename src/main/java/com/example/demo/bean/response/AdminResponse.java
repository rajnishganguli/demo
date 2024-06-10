package com.example.demo.bean.response;

import com.example.demo.bean.model.AdminDetail;

import java.io.Serializable;

public class AdminResponse implements Serializable {
    Long id;
    String name;
    String userName;

    public AdminResponse() {
    }

    public AdminResponse(Long id, String name, String userName, String address) {
        this.id = id;
        this.name = name;
        this.userName = userName;
    }

    public AdminResponse(AdminDetail adminDetail) {
        this.id = adminDetail.getId();
        this.name = adminDetail.getName();
        this.userName = adminDetail.getUserName();
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

}
