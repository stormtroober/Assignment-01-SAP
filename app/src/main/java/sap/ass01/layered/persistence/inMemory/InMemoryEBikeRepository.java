package sap.ass01.layered.persistence.inMemory;

import com.google.gson.Gson;
import sap.ass01.layered.persistence.DTO.EBikeDTO;
import sap.ass01.layered.persistence.Repositories.EBikeRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class InMemoryEBikeRepository implements EBikeRepository {
    @Override
    public EBikeDTO findEBikeById(int bikeId) {
        return null;
    }

    @Override
    public List<EBikeDTO> findAllEBikes() {
        return List.of();
    }

    @Override
    public void saveEBike(EBikeDTO eBike) {
        Gson gson = new Gson();
        String json = gson.toJson(eBike);

        try (FileWriter writer = new FileWriter("ebike.json", true)) {
            writer.write(json);
            writer.write(System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEBike(EBikeDTO eBike) {

    }
}
