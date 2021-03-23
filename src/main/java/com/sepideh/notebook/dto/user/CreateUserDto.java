package com.sepideh.notebook.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto implements Serializable {

    private String username;
    private String password;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
