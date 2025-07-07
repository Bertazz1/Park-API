package com.Bertazz1.demo_park_api.service;


import com.Bertazz1.demo_park_api.entity.Client;
import com.Bertazz1.demo_park_api.entity.ClientSpace;
import com.Bertazz1.demo_park_api.entity.ParkingSpace;
import com.Bertazz1.demo_park_api.util.ParkingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ParkingService {

    private final ClientSpaceService clientSpaceService;
    private final ParkingSpaceService parkingSpaceService;
    private final ClientService clientService;

    @Transactional
    public ClientSpace checkIn(ClientSpace clientSpace) {
        Client client  = clientService.findByCpf(clientSpace.getClient().getCpf());
        clientSpace.setClient(client);

        ParkingSpace parkingSpace = parkingSpaceService.findByAvailableSpace();
        parkingSpace.setStatus(ParkingSpace.StatusParking.OCCUPIED);
        clientSpace.setParkingSpace(parkingSpace);

        clientSpace.setEntryTime(LocalDateTime.now());

        clientSpace.setReceipt(ParkingUtils.generateRecipt());

        return clientSpaceService.createClientSpace(clientSpace);
    }
}
