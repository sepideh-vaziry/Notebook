package com.sepideh.notebook.mapper;

import com.sepideh.notebook.domain.User;
import com.sepideh.notebook.dto.user.UserListDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    List<UserListDto> toDto(List<User> users);

}
