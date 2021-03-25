package com.sepideh.notebook.mapper;

import com.sepideh.notebook.dto.media.MediaFileDto;
import com.sepideh.notebook.model.MediaFile;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class MediaFileMapper {

    @Value("${project.serer.url}")
    private String serveURL;

    //******************************************************************************************************************
    @Mapping(target = "file", ignore = true)
    public abstract MediaFileDto toDto(MediaFile mediaFile);

    public abstract List<MediaFileDto> toDto(List<MediaFile> mediaFiles);

    //******************************************************************************************************************
    public abstract MediaFile toModel(MediaFileDto mediaFileDto);

    //******************************************************************************************************************
    public abstract MediaFile update(MediaFileDto mediaFileDto, @MappingTarget MediaFile mediaFile);

    //******************************************************************************************************************
    @AfterMapping
    protected void generatePath(@MappingTarget MediaFileDto mediaFileDto) {
        mediaFileDto.setPath(serveURL.concat(mediaFileDto.getPath()));
    }

}
