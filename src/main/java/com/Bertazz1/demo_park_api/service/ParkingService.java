package com.Bertazz1.demo_park_api.service;


import com.Bertazz1.demo_park_api.entity.Client;
import com.Bertazz1.demo_park_api.entity.ClientSpace;
import com.Bertazz1.demo_park_api.entity.ParkingSpace;
import com.Bertazz1.demo_park_api.util.ParkingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

        clientSpace.setReceipt(ParkingUtils.generateReceipt());

        return clientSpaceService.createClientSpace(clientSpace);
    }
    @Transactional
    public ClientSpace checkOut(String receipt) {
        ClientSpace clientSpace = clientSpaceService.findByReceipt(receipt);

        LocalDateTime exitTime = LocalDateTime.now();

        BigDecimal price = ParkingUtils.calculateCost(clientSpace.getEntryTime(), exitTime);
        clientSpace.setPrice(price);

        long totalTimes = clientSpaceService.getTotalTimesCompleteParking(clientSpace.getClient().getCpf());

        BigDecimal discount = ParkingUtils.calculateDiscount(price, totalTimes);
        clientSpace.setDiscount(discount);

        clientSpace.setExitTime(exitTime);
        clientSpace.getParkingSpace().setStatus(ParkingSpace.StatusParking.AVAILABLE);

        return clientSpaceService.createClientSpace(clientSpace);

    }
}
