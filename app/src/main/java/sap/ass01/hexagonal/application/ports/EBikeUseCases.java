package sap.ass01.hexagonal.application.ports;

import sap.ass01.hexagonal.application.entities.EBikeDTO;

import java.util.List;
import java.util.Optional;

public interface EBikeUseCases {
    void registerEBike(EBikeDTO ebikeDTO);
    Optional<EBikeDTO> findEBikeById(String id);
    List<EBikeDTO> getAllEBikes();
}