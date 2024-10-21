package sap.ass01.hexagonal.application;

import sap.ass01.hexagonal.application.entities.EBikeDTO;
import sap.ass01.hexagonal.domain.model.EBike;

import java.util.Optional;

public interface EbikeApplication {
    void addEbike(String id, double x, double y, EBike.EBikeState state, int battery);
    Optional<EBikeDTO> getEbike(String id);
    void updateBike(String id, double x, double y, EBike.EBikeState state, int battery);

}
