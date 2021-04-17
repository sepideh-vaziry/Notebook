package com.sepideh.notebook.mapper;

import com.sepideh.notebook.dto.content.ContentDto;
import com.sepideh.notebook.dto.content.SimpleContentDto;
import com.sepideh.notebook.model.Content;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, UserMapper.class})
public interface ContentMapper {

    //******************************************************************************************************************
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "categories", target = "categoryIds")
    ContentDto toDto(Content content);

    @Mapping(source = "user.id", target = "userId")
    SimpleContentDto toSimpleDto(Content content);

    //******************************************************************************************************************
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "categoryIds", target = "categories")
    Content toModel(ContentDto contentDto);

    //******************************************************************************************************************
    Content update(ContentDto contentDto, @MappingTarget Content content);

    //******************************************************************************************************************
    List<SimpleContentDto> toDto(List<Content> contents);

}
