package com.sepideh.notebook.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtAuth implements Serializable {

    private String username;
    private String password;

    private String access;

    // Constructor *****************************************************************************************************
    public JwtAuth() { }

    public JwtAuth(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public JwtAuth(String access) {
        this.access = access;
    }

    //******************************************************************************************************************
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

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }
}
