package com.Bertazz1.demo_park_api.repository;

import com.Bertazz1.demo_park_api.entity.ClientSpace;
import com.Bertazz1.demo_park_api.repository.projection.ClientSpaceProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientSpaceRepository  extends JpaRepository<ClientSpace, Long> {
    Optional<ClientSpace> findByReceiptAndExitTimeIsNull(String receipt);

    long countByClientCpfAndExitTimeIsNotNull(String cpf);

    Page<ClientSpaceProjection> findAllByClientCpf(String cpf, Pageable pageable);

    Page<ClientSpaceProjection> findAllByClientUserId(Long id, Pageable pageable);
}
