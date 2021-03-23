package com.sepideh.notebook.mapper;

import com.sepideh.notebook.dto.user.CreateUserDto;
import com.sepideh.notebook.dto.user.SimpleUserDto;
import com.sepideh.notebook.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    //******************************************************************************************************************
    SimpleUserDto toDto(User user);

    List<SimpleUserDto> toDto(List<User> users);

    //******************************************************************************************************************
    User toModel(SimpleUserDto simpleUserDto);

    User toModel(CreateUserDto createUserDto);

    //******************************************************************************************************************
    User update(SimpleUserDto userDto, @MappingTarget User user);

}
