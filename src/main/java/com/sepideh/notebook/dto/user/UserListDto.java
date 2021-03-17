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
public class UserListDto implements Serializable {

    private Long id;
    private String username;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
