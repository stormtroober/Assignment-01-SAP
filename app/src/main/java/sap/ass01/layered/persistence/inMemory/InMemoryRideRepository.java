package sap.ass01.layered.persistence.inMemory;

import sap.ass01.layered.persistence.DTO.RideDTO;
import sap.ass01.layered.persistence.repository.RideRepository;

import java.util.List;

public class InMemoryRideRepository implements RideRepository {

    @Override
    public RideDTO findRideById(int rideId) {
        return null;
    }

    @Override
    public List<RideDTO> findAllRides() {
        return List.of();
    }

    @Override
    public void saveRide(RideDTO ride) {

    }

    @Override
    public void updateRide(RideDTO ride) {

    }
}
