// DiskEBikeRepositoryImpl.java
package sap.ass01.hexagonal.infrastructure.adapters.infrastructure.persistence;

import sap.ass01.hexagonal.application.entities.EBikeDTO;
import sap.ass01.hexagonal.application.ports.EBikeRepository;
import sap.ass01.hexagonal.infrastructure.adapters.infrastructure.db.disk.DiskEBikeDatabase;

import java.util.List;
import java.util.Optional;

public class DiskEBikeRepositoryImpl implements EBikeRepository {

    private final DiskEBikeDatabase diskEBikeDatabase;

    public DiskEBikeRepositoryImpl() {
        this.diskEBikeDatabase = new DiskEBikeDatabase();
    }

    @Override
    public Optional<EBikeDTO> findEBikeById(String bikeId) {
        return diskEBikeDatabase.findById(bikeId);
    }

    @Override
    public List<EBikeDTO> findAllEBikes() {
        return diskEBikeDatabase.findAll();
    }

    @Override
    public void saveEBike(EBikeDTO eBike) {
        diskEBikeDatabase.save(eBike);
    }

    @Override
    public void updateEBike(EBikeDTO eBike) {
        diskEBikeDatabase.update(eBike);
    }

//    @Override
//    public void cleanDatabase() {
//        diskEBikeDatabase.clean();
//    }
}