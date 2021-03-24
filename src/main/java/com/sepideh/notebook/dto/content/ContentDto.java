package com.sepideh.notebook.dto.content;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContentDto {

    private Long id;
    private String title;
    private String description;
    private long userId;
    private List<Long> categoryIds;

}
