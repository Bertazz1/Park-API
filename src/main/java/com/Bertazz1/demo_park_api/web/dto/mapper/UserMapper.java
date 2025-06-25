package com.Bertazz1.demo_park_api.web.dto.mapper;

import com.Bertazz1.demo_park_api.entity.User;
import com.Bertazz1.demo_park_api.web.dto.UserCreateDto;
import com.Bertazz1.demo_park_api.web.dto.UserResposeDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toUser (UserCreateDto userCreateDto) {
        return new ModelMapper().map(userCreateDto, User.class);
    }

    public static UserResposeDto toDto (User user) {
        String role = user.getRole().name().substring("ROLE_".length());
        PropertyMap<User, UserResposeDto> propertyMap = new PropertyMap<User, UserResposeDto>() {
            @Override
            protected void configure() {
               map().setRole(role);
            }
        };
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(propertyMap);
        return modelMapper.map(user, UserResposeDto.class);
    }

    public static List<UserResposeDto> toListDto(List<User> users) {
        return users.stream()
                .map(User -> toDto(User)).collect(Collectors.toList());
    }
}
