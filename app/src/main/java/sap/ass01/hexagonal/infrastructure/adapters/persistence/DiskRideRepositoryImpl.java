// DiskRideRepositoryImpl.java
package sap.ass01.hexagonal.infrastructure.adapters.persistence;

import sap.ass01.hexagonal.application.entities.RideDTO;
import sap.ass01.hexagonal.application.ports.RideRepository;
import sap.ass01.hexagonal.infrastructure.diskdb.DiskRideDBAdapter;

import java.util.List;
import java.util.Optional;

public class DiskRideRepositoryImpl implements RideRepository {

    private final DiskRideDBAdapter diskRideDatabase;

    public DiskRideRepositoryImpl() {
        this.diskRideDatabase = new DiskRideDBAdapter();
    }

    @Override
    public Optional<RideDTO> findRideById(String rideId) {
        return diskRideDatabase.findById(rideId);
    }

    @Override
    public List<RideDTO> findAllRides() {
        return diskRideDatabase.findAll();
    }

    @Override
    public void saveRide(RideDTO ride) {
        diskRideDatabase.save(ride);
    }

    @Override
    public void updateRide(RideDTO ride) {
        diskRideDatabase.update(ride);
    }

    @Override
    public void clean() {
        diskRideDatabase.clean();
    }
}