package sap.ass01.layered.persistence.ride;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import sap.ass01.layered.persistence.dto.RideDTO;
import sap.ass01.layered.persistence.repository.RideRepository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class DiskRideRepository implements RideRepository {

    private final Gson gson;
    private static final File SAVE_FILE = new File("persistence/in_memory/rides.json");

    public DiskRideRepository() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(new TypeToken<Optional<Date>>() {}.getType(), new OptionalTypeAdapter<Date>())
                .create();
    }

    @Override
    public Optional<RideDTO> findRideById(String rideId) {
        if (SAVE_FILE.exists()) {
            List<RideDTO> rides = readRidesFromFile();
            return rides.stream()
                    .filter(ride -> Objects.equals(ride.getId(), rideId))
                    .findFirst();
        }
        return Optional.empty();
    }

    @Override
    public List<RideDTO> findAllRides() {
        return readRidesFromFile();
    }

    @Override
    public void saveRide(RideDTO ride) {
        if (SAVE_FILE.exists()) {
            List<RideDTO> rides = readRidesFromFile();
            boolean rideExists = rides.stream()
                    .anyMatch(existingRide -> Objects.equals(existingRide.getId(), ride.getId()));

            if (!rideExists) {
                rides.add(ride);
                writeRidesToFile(rides);
            }
        } else {
            List<RideDTO> rides = new ArrayList<>();
            rides.add(ride);
            writeRidesToFile(rides);
        }
    }

    @Override
    public void updateRide(RideDTO ride) {
        if (SAVE_FILE.exists()) {
            List<RideDTO> rides = readRidesFromFile();
            for (int i = 0; i < rides.size(); i++) {
                if (Objects.equals(rides.get(i).getId(), ride.getId())) {
                    rides.set(i, ride);
                    writeRidesToFile(rides);
                    return;
                }
            }
        }
    }

    @Override
    public void cleanDatabase() {
        if (SAVE_FILE.exists()) {
            SAVE_FILE.delete();
        }
    }

    private List<RideDTO> readRidesFromFile() {
        try (FileReader reader = new FileReader(SAVE_FILE)) {
            Type rideListType = new TypeToken<ArrayList<RideDTO>>() {}.getType();
            List<RideDTO> rides;

            // Read the JSON content as a generic Object
            Object jsonContent = gson.fromJson(reader, Object.class);

            // Check if the content is an array or a single object
            if (jsonContent instanceof List) {
                rides = gson.fromJson(gson.toJson(jsonContent), rideListType);
            } else {
                RideDTO singleRide = gson.fromJson(gson.toJson(jsonContent), RideDTO.class);
                rides = new ArrayList<>();
                rides.add(singleRide);
            }

            return rides;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void writeRidesToFile(List<RideDTO> rides) {
        try (FileWriter writer = new FileWriter(SAVE_FILE)) {
            gson.toJson(rides, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}