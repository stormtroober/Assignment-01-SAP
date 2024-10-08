package sap.ass01.layered.persistence.Repositories;

import java.util.List;

public interface EBikeRepository {
    EBikeDTOTemp findEBikeById(int bikeId);
    List<EBikeDTOTemp> findAllEBikes();
    void saveEBike(EBikeDTOTemp ebike);
    void updateEBike(EBikeDTOTemp ebike);
}
