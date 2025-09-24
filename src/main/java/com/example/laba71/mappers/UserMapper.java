package com.example.laba71.mappers;

import com.example.laba71.dto.ProfileDto;
import com.example.laba71.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CentralMapperConfig.class)
public interface UserMapper {
    @Mapping(target = "readerName", source = "name")
    @Mapping(target = "libraryCardNumber", source = "libraryCardNumber")
    @Mapping(target = "loans", ignore = true)
    @Mapping(target = "fromDate", ignore = true)
    @Mapping(target = "toDate", ignore = true)
    ProfileDto toProfile(User e);
}
