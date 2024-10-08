package sap.ass01.layered.persistence.inMemory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import sap.ass01.layered.persistence.DTO.EBikeDTO;
import sap.ass01.layered.persistence.repository.EBikeRepository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class InMemoryEBikeRepository implements EBikeRepository {

    private final Gson gson = new Gson();
    private static final File SAVE_FILE = new File("persistence/in_memory/ebikes.json");

    @Override
    public Optional<EBikeDTO> findEBikeById(String ebikeId) {
        if (SAVE_FILE.exists()) {
            List<EBikeDTO> ebikes = readEBikesFromFile();
            return ebikes.stream()
                    .filter(ebike -> Objects.equals(ebike.getId(), ebikeId))
                    .findFirst();
        }
        return Optional.empty();
    }

    @Override
    public List<EBikeDTO> findAllEBikes() {
        return readEBikesFromFile();
    }

    @Override
    public void updateEBike(EBikeDTO ebike) {
        if (SAVE_FILE.exists()) {
            List<EBikeDTO> ebikes = readEBikesFromFile();
            for (int i = 0; i < ebikes.size(); i++) {
                if (Objects.equals(ebikes.get(i).getId(), ebike.getId())) {
                    ebikes.set(i, ebike);
                    writeEBikesToFile(ebikes);
                    return;
                }
            }
        }
    }

    @Override
    public void saveEBike(EBikeDTO ebike) {
        if (SAVE_FILE.exists()) {
            List<EBikeDTO> ebikes = readEBikesFromFile();
            boolean ebikeExists = ebikes.stream()
                    .anyMatch(existingEBike -> Objects.equals(existingEBike.getId(), ebike.getId()));

            if (!ebikeExists) {
                ebikes.add(ebike);
                writeEBikesToFile(ebikes);
            }
        } else {
            List<EBikeDTO> ebikes = new ArrayList<>();
            ebikes.add(ebike);
            writeEBikesToFile(ebikes);
        }
    }

    private List<EBikeDTO> readEBikesFromFile() {
        try (FileReader reader = new FileReader(SAVE_FILE)) {
            Type ebikeListType = new TypeToken<ArrayList<EBikeDTO>>() {}.getType();
            List<EBikeDTO> ebikes;

            // Read the JSON content as a generic Object
            Object jsonContent = gson.fromJson(reader, Object.class);

            // Check if the content is an array or a single object
            if (jsonContent instanceof List) {
                ebikes = gson.fromJson(gson.toJson(jsonContent), ebikeListType);
            } else {
                EBikeDTO singleEBike = gson.fromJson(gson.toJson(jsonContent), EBikeDTO.class);
                ebikes = new ArrayList<>();
                ebikes.add(singleEBike);
            }

            return ebikes;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void writeEBikesToFile(List<EBikeDTO> ebikes) {
        try (FileWriter writer = new FileWriter(SAVE_FILE)) {
            gson.toJson(ebikes, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}