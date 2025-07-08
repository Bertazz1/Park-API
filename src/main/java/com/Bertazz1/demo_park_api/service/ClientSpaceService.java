package com.Bertazz1.demo_park_api.service;


import com.Bertazz1.demo_park_api.entity.ClientSpace;
import com.Bertazz1.demo_park_api.exception.EntityNotFoundException;
import com.Bertazz1.demo_park_api.repository.ClientSpaceRepository;
import com.Bertazz1.demo_park_api.repository.projection.ClientSpaceProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @Transactional(readOnly = true)
    public Page<ClientSpaceProjection> findAllByCpf(String cpf, Pageable pageable) {
        return clientSpaceRepository.findAllByClientCpf(cpf, pageable);

    }
    @Transactional(readOnly = true)
    public Page<ClientSpaceProjection> findAllByUserId(Long id, Pageable pageable) {
        return clientSpaceRepository.findAllByClientUserId(id, pageable);
    }
}
