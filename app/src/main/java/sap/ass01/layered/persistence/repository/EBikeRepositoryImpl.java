package sap.ass01.layered.persistence.repository;

import sap.ass01.layered.database.Database;
import sap.ass01.layered.database.EBikeDatabaseFactory;
import sap.ass01.layered.persistence.mapper.DatabaseToPersistenceMapper;
import sap.ass01.layered.persistence.mapper.PersistenceToDatabaseMapper;
import sap.ass01.layered.persistence.dto.EBikeDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EBikeRepositoryImpl implements EBikeRepository {

    private final Database<sap.ass01.layered.database.dto.EBikeDTO> database;

    public EBikeRepositoryImpl() {
        this.database = EBikeDatabaseFactory.createDatabase();
    }

    @Override
    public Optional<EBikeDTO> findEBikeById(String bikeId) {
        return database.findById(bikeId)
                .map(DatabaseToPersistenceMapper::toPersistenceDTO);
    }

    @Override
    public List<EBikeDTO> findAllEBikes() {
        return database.findAll().stream()
                .map(DatabaseToPersistenceMapper::toPersistenceDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void saveEBike(EBikeDTO eBike) {
        database.save(PersistenceToDatabaseMapper.toDatabaseDTO(eBike));
    }

    @Override
    public void updateEBike(EBikeDTO eBike) {
        database.update(PersistenceToDatabaseMapper.toDatabaseDTO(eBike));
    }

    @Override
    public void cleanDatabase() {
        database.clean();
    }
}