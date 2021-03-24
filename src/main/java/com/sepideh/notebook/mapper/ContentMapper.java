package com.sepideh.notebook.mapper;

import com.sepideh.notebook.dto.content.ContentDto;
import com.sepideh.notebook.dto.content.SimpleContentDto;
import com.sepideh.notebook.model.Content;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ContentMapper {

    //******************************************************************************************************************
    @Mapping(source = "content.user.id", target = "userId")
    @Mapping(source = "content.categories", target = "categoryIds")
    ContentDto toDto(Content content);

    @Mapping(source = "content.user.id", target = "userId")
    SimpleContentDto toSimpleDto(Content content);

    //******************************************************************************************************************
    @Mapping(source = "contentDto.userId", target = "user.id")
    @Mapping(source = "contentDto.categoryIds", target = "categories")
    Content toModel(ContentDto contentDto);

    //******************************************************************************************************************
    Content update(ContentDto contentDto, @MappingTarget Content content);

    //******************************************************************************************************************
    List<SimpleContentDto> toDto(List<Content> contents);

}
