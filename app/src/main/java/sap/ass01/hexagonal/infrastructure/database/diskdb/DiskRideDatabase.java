package sap.ass01.hexagonal.infrastructure.database.diskdb;

import com.google.gson.reflect.TypeToken;
import sap.ass01.hexagonal.application.ports.entities.RideDTO;
import sap.ass01.hexagonal.infrastructure.database.Database;

import java.util.List;

public class DiskRideDatabase extends DiskDatabaseImpl<RideDTO> implements Database<RideDTO> {

    public DiskRideDatabase() {
        super("hexagonal/persistence/disk/rides.json", RideDTO::id, new TypeToken<List<RideDTO>>() {}.getType());
    }
}