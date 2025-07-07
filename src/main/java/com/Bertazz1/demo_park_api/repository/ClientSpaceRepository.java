package com.Bertazz1.demo_park_api.repository;

import com.Bertazz1.demo_park_api.entity.ClientSpace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientSpaceRepository  extends JpaRepository<ClientSpace, Long> {
}
