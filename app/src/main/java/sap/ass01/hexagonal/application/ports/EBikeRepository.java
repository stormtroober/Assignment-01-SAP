package sap.ass01.hexagonal.application.ports;


import sap.ass01.hexagonal.application.ports.entities.EBikeDTO;

import java.util.List;
import java.util.Optional;

public interface EBikeRepository {
    Optional<EBikeDTO> findEBikeById(String bikeId);
    List<EBikeDTO> findAllEBikes();
    void saveEBike(EBikeDTO eBike);
    void updateEBike(EBikeDTO eBike);
    void clean();
}
