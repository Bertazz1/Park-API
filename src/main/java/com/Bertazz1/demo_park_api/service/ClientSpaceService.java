package com.Bertazz1.demo_park_api.service;


import com.Bertazz1.demo_park_api.entity.ClientSpace;
import com.Bertazz1.demo_park_api.repository.ClientSpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientSpaceService {

    private final ClientSpaceRepository clientSpaceRepository;


    @Transactional
    public ClientSpace createClientSpace(ClientSpace clientSpace) {
        return clientSpaceRepository.save(clientSpace);
    }
}
