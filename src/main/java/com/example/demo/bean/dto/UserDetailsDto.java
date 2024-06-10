package com.example.demo.bean.dto;

import com.example.demo.bean.model.AdminDetail;
import com.example.demo.bean.model.CustomerDetail;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class UserDetailsDto implements UserDetails {
    private Long id;
    private String userName;
    private String password;
    private Set<String> roles = new HashSet<>();
    private List<GrantedAuthority> authorities = new ArrayList<>();

    public UserDetailsDto(AdminDetail adminDetail) {
        userName = adminDetail.getUserName();
        password = adminDetail.getPassword();
        authorities = new ArrayList<>();
        roles = new HashSet<>();
    }

    public UserDetailsDto(CustomerDetail customerDetail) {
        userName = customerDetail.getUserName();
        password = customerDetail.getPassword();
        authorities = new ArrayList<>();
        roles = new HashSet<>();
    }

    public UserDetailsDto() {
        authorities = new ArrayList<>();
        roles = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

