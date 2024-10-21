package sap.ass01.layered.persistence.repository;

import sap.ass01.layered.database.Database;
import sap.ass01.layered.database.RideDatabaseFactory;
import sap.ass01.layered.persistence.mapper.DatabaseToPersistenceMapper;
import sap.ass01.layered.persistence.mapper.PersistenceToDatabaseMapper;
import sap.ass01.layered.persistence.dto.RideDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RideRepositoryImpl implements RideRepository {

    private final Database<sap.ass01.layered.database.dto.RideDTO> database;

    public RideRepositoryImpl() {
        this.database = RideDatabaseFactory.createDatabase();
    }

    @Override
    public Optional<RideDTO> findRideById(String rideId) {
        return database.findById(rideId)
                .map(DatabaseToPersistenceMapper::toPersistenceDTO);
    }

    @Override
    public List<RideDTO> findAllRides() {
        return database.findAll().stream()
                .map(DatabaseToPersistenceMapper::toPersistenceDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void saveRide(RideDTO ride) {
        database.save(PersistenceToDatabaseMapper.toDatabaseDTO(ride));
    }

    @Override
    public void updateRide(RideDTO ride) {
        database.update(PersistenceToDatabaseMapper.toDatabaseDTO(ride));
    }

    @Override
    public void cleanDatabase() {
        database.clean();
    }
}