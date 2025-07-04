package com.Bertazz1.demo_park_api.service;


import com.Bertazz1.demo_park_api.entity.Client;
import com.Bertazz1.demo_park_api.exception.CpfUniqueViolationException;
import com.Bertazz1.demo_park_api.exception.EntityNotFoundException;
import com.Bertazz1.demo_park_api.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));
    }
}
