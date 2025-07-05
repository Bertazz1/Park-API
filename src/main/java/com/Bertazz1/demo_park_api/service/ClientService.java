package com.Bertazz1.demo_park_api.service;


import com.Bertazz1.demo_park_api.entity.Client;
import com.Bertazz1.demo_park_api.exception.CpfUniqueViolationException;
import com.Bertazz1.demo_park_api.exception.EntityNotFoundException;
import com.Bertazz1.demo_park_api.repository.ClientRepository;
import com.Bertazz1.demo_park_api.repository.projection.ClientProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@RequiredArgsConstructor
@Service
public class ClientService {

    private final ClientRepository clientRepository;


    @Transactional
    public Client createClient(Client client) {
        try {
            return clientRepository.save(client);
        }
        catch (DataIntegrityViolationException ex) {
            throw new CpfUniqueViolationException(
                String.format("CPF '%s' is already in use", client.getCpf()));
        }
    }
    @Transactional(readOnly = true)
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));
    }
    @Transactional(readOnly = true)
    public Page<ClientProjection> findAll(Pageable pageable) {
       return  clientRepository.findAllPageable(pageable);

    }

    @Transactional(readOnly = true)
    public Client findByUserId(Long id) {
        return clientRepository.findByUserId(id);

    }
}
