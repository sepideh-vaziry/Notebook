package com.sepideh.notebook.dto.content;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleContentDto {

    private Long id;

    @NotBlank
    private String title;

    private String description;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private long userId;

}
