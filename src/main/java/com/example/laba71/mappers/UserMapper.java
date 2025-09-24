package com.example.laba71.mappers;

import com.example.laba71.dto.ProfileDto;
import com.example.laba71.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    ProfileDto toDto(User user);
    User toEntity(ProfileDto profileDto);
}
