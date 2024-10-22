package sap.ass01.hexagonal.application.ports;

import sap.ass01.hexagonal.application.entities.EBikeDTO;
import sap.ass01.hexagonal.application.entities.RideDTO;

import java.util.List;
import java.util.Optional;

public interface RideRepository {
    Optional<RideDTO> findRideById(String rideId);
    List<RideDTO> findAllRides();
    void saveRide(RideDTO ride);
    void updateRide(RideDTO ride);
    void clean();
}
