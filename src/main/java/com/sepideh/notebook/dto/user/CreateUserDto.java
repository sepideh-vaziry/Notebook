package com.sepideh.notebook.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto implements Serializable {

    @NotBlank
    private String username;
    private String password;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
