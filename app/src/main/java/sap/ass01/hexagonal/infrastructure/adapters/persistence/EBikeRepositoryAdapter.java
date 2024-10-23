package sap.ass01.hexagonal.infrastructure.adapters.persistence;

import sap.ass01.hexagonal.application.ports.EBikeRepository;
import sap.ass01.hexagonal.application.ports.entities.EBikeDTO;
import sap.ass01.hexagonal.infrastructure.database.Database;
import sap.ass01.hexagonal.infrastructure.database.EBikeDatabaseFactory;

import java.util.List;
import java.util.Optional;

public class EBikeRepositoryAdapter implements EBikeRepository {

    private final Database<EBikeDTO> eBikeDatabase;

    public EBikeRepositoryAdapter(DatabaseType databaseType) {
        this.eBikeDatabase = EBikeDatabaseFactory.getDatabase(databaseType);
    }

    @Override
    public Optional<EBikeDTO> findEBikeById(String bikeId) {
        return eBikeDatabase.findById(bikeId);
    }

    @Override
    public List<EBikeDTO> findAllEBikes() {
        return eBikeDatabase.findAll();
    }

    @Override
    public void saveEBike(EBikeDTO eBike) {
        eBikeDatabase.save(eBike);
    }

    @Override
    public void updateEBike(EBikeDTO eBike) {
        eBikeDatabase.update(eBike);
    }

    @Override
    public void clean() {
        eBikeDatabase.clean();
    }
}