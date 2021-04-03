package com.sepideh.notebook.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtAuth implements Serializable {

    private String username;
    private String password;

    private String access;
    private String refresh;

    // Constructor *****************************************************************************************************
    public JwtAuth(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
