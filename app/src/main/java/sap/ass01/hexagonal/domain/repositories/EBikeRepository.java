package sap.ass01.hexagonal.domain.repositories;

import sap.ass01.hexagonal.domain.entities.EBike;
import sap.ass01.layered.persistence.dto.EBikeDTO;

import java.util.List;
import java.util.Optional;

public interface EBikeRepository {
    Optional<EBike> findEBikeById(String bikeId);
    List<EBike> findAllEBikes();
    void saveEBike(EBike eBike);
    void updateEBike(EBike eBike);
}
