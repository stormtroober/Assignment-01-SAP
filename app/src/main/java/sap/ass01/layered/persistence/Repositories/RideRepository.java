package sap.ass01.layered.persistence.Repositories;
import sap.ass01.layered.persistence.RideDTOTemp;

import java.util.List;

public interface RideRepository {
    RideDTOTemp findRideById(int rideId);
    List<RideDTOTemp> findAllRides();
    void saveRide(RideDTOTemp ride);
    void updateRide(RideDTOTemp ride);
}
