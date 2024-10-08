package sap.ass01.layered.persistence.inMemory;

//import sap.ass01.layered.domain.Ride;
import sap.ass01.layered.persistence.Repositories.RideRepository;
import sap.ass01.layered.persistence.RideDTOTemp;

import java.util.List;

public class InMemoryRideRepository implements RideRepository {
    @Override
    public RideDTOTemp findRideById(int rideId) {
        return null;
    }

    @Override
    public List<RideDTOTemp> findAllRides() {
        return List.of();
    }

    @Override
    public void saveRide(RideDTOTemp ride) {

    }

    @Override
    public void updateRide(RideDTOTemp ride) {

    }
}
