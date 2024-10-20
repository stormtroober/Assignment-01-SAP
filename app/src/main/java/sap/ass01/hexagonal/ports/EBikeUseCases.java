package sap.ass01.hexagonal.ports;

import sap.ass01.hexagonal.domain.entities.EBike;

public interface EBikeUseCases {
    void registerEBike(String id, String model);
    EBike getEBikeInfo(String ebikeId);
}
