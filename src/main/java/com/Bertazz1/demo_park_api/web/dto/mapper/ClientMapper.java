package com.Bertazz1.demo_park_api.web.dto.mapper;

import com.Bertazz1.demo_park_api.entity.Client;
import com.Bertazz1.demo_park_api.web.dto.ClientCreateDto;
import com.Bertazz1.demo_park_api.web.dto.ClientResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ClientMapper {

    public static Client toClient(ClientCreateDto dto){
        return new ModelMapper().map(dto,Client.class);
    }

    public static ClientResponseDto toDto(Client client){
        return new ModelMapper().map(client, ClientResponseDto.class);
    }



}
