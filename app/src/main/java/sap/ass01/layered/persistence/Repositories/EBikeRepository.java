package sap.ass01.layered.persistence.Repositories;

import sap.ass01.layered.persistence.DTO.EBikeDTO;

import java.util.List;

public interface EBikeRepository {
    EBikeDTO findEBikeById(int bikeId);
    List<EBikeDTO> findAllEBikes();
    void saveEBike(EBikeDTO eBike);
    void updateEBike(EBikeDTO eBike);
}
