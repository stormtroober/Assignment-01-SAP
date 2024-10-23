// DiskEBikeRepositoryImpl.java
package sap.ass01.hexagonal.infrastructure.adapters.persistence;

import sap.ass01.hexagonal.application.entities.EBikeDTO;
import sap.ass01.hexagonal.application.ports.EBikeRepository;
import sap.ass01.hexagonal.infrastructure.diskdb.DiskEBikeDBAdapter;

import java.util.List;
import java.util.Optional;

public class DiskEBikeRepositoryImpl implements EBikeRepository {

    private final DiskEBikeDBAdapter diskEBikeDatabase;

    public DiskEBikeRepositoryImpl() {
        this.diskEBikeDatabase = new DiskEBikeDBAdapter();
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

    @Override
    public void clean() {
        diskEBikeDatabase.clean();
    }
}