package sap.ass01.layered.persistence.inMemory;

//import sap.ass01.layered.domain.EBike;
import sap.ass01.layered.persistence.Repositories.EBikeDTOTemp;
import sap.ass01.layered.persistence.Repositories.EBikeRepository;

import java.util.List;

public class InMemoryEBikeRepository implements EBikeRepository {
    @Override
    public EBikeDTOTemp findEBikeById(int bikeId) {
        return null;
    }

    @Override
    public List<EBikeDTOTemp> findAllEBikes() {
        return List.of();
    }

    @Override
    public void saveEBike(EBikeDTOTemp ebike) {

    }

    @Override
    public void updateEBike(EBikeDTOTemp ebike) {

    }
}
