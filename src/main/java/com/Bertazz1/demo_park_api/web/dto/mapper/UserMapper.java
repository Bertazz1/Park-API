package com.Bertazz1.demo_park_api.web.dto.mapper;

import com.Bertazz1.demo_park_api.entity.User;
import com.Bertazz1.demo_park_api.web.dto.UserCreateDto;
import com.Bertazz1.demo_park_api.web.dto.UserResposeDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.Properties;

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
}
