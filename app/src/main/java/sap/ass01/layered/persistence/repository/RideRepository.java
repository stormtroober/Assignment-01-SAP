package sap.ass01.layered.persistence.repository;

import sap.ass01.layered.persistence.dto.RideDTO;

import java.util.List;
import java.util.Optional;

public interface RideRepository {
    Optional<RideDTO> findRideById(String rideId);
    List<RideDTO> findAllRides();
    void saveRide(RideDTO ride);
    void updateRide(RideDTO ride);
    void cleanDatabase();
}
