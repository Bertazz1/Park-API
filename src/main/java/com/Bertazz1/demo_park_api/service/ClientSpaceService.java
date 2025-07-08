package com.Bertazz1.demo_park_api.service;


import com.Bertazz1.demo_park_api.entity.ClientSpace;
import com.Bertazz1.demo_park_api.exception.EntityNotFoundException;
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

    @Transactional(readOnly = true)
    public ClientSpace findByReceipt(String receipt) {
        return clientSpaceRepository.findByReceiptAndExitTimeIsNull(receipt)
                .orElseThrow(() -> new EntityNotFoundException("Client space not found or checkout already done with receipt: " + receipt));
    }
    @Transactional(readOnly = true)
    public long getTotalTimesCompleteParking(String cpf) {
        return clientSpaceRepository.countByClientCpfAndExitTimeIsNotNull(cpf);
    }
}
