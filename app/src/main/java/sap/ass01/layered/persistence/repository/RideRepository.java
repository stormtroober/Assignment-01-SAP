package sap.ass01.layered.persistence.repository;

import sap.ass01.layered.persistence.DTO.RideDTO;

import java.util.List;

public interface RideRepository {
    RideDTO findRideById(int rideId);
    List<RideDTO> findAllRides();
    void saveRide(RideDTO ride);
    void updateRide(RideDTO ride);
}
