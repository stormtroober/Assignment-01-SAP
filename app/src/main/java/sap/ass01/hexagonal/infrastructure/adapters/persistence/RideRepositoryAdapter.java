package sap.ass01.hexagonal.infrastructure.adapters.persistence;

import sap.ass01.hexagonal.application.ports.RideRepository;
import sap.ass01.hexagonal.application.ports.entities.RideDTO;
import sap.ass01.hexagonal.infrastructure.database.Database;
import sap.ass01.hexagonal.infrastructure.database.RideDatabaseFactory;

import java.util.List;
import java.util.Optional;

public class RideRepositoryAdapter implements RideRepository {

    private final Database<RideDTO> rideDatabase;

    public RideRepositoryAdapter(DatabaseType databaseType) {
        this.rideDatabase = RideDatabaseFactory.getDatabase(databaseType);
    }

    @Override
    public Optional<RideDTO> findRideById(String rideId) {
        return rideDatabase.findById(rideId);
    }

    @Override
    public List<RideDTO> findAllRides() {
        return rideDatabase.findAll();
    }

    @Override
    public void saveRide(RideDTO ride) {
        rideDatabase.save(ride);
    }

    @Override
    public void updateRide(RideDTO ride) {
        rideDatabase.update(ride);
    }

    @Override
    public void clean() {
        rideDatabase.clean();
    }
}