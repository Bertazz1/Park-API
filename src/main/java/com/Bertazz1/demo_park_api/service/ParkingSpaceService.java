package com.Bertazz1.demo_park_api.service;


import com.Bertazz1.demo_park_api.entity.ParkingSpace;
import com.Bertazz1.demo_park_api.exception.CodeUniqueViolationException;
import com.Bertazz1.demo_park_api.exception.EntityNotFoundException;
import com.Bertazz1.demo_park_api.repository.ParkingSpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParkingSpaceService {

    private final ParkingSpaceRepository parkingSpaceRepository;

    @Transactional
    public ParkingSpace createParkingSpace(ParkingSpace parkingSpace) {
        try {
            return parkingSpaceRepository.save(parkingSpace);
        } catch (DataIntegrityViolationException e) {
            throw new CodeUniqueViolationException("Error creating parking space with code: " + parkingSpace.getCode());
        }
    }

    @Transactional(readOnly = true)
    public ParkingSpace findByCode(String code) {
        return parkingSpaceRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Parking space not found with code: " + code));
    }
}
