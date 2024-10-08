package sap.ass01.layered.persistence.repository;

import sap.ass01.layered.persistence.DTO.EBikeDTO;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface EBikeRepository {

    Optional<EBikeDTO> findEBikeById(String bikeId);
    List<EBikeDTO> findAllEBikes();
    void saveEBike(EBikeDTO eBike);
    void updateEBike(EBikeDTO eBike);

}
