package com.sepideh.notebook.dto.media;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MediaFileDto implements Serializable {

    private Long id;
    private String path;

    @JsonIgnore
    private MultipartFile file;

    private Timestamp createdAt;
    private Timestamp updatedAt;

}
