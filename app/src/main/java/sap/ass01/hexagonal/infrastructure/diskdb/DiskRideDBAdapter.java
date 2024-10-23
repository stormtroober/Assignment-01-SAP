package sap.ass01.hexagonal.infrastructure.diskdb;

import com.google.gson.reflect.TypeToken;
import sap.ass01.hexagonal.application.ports.entities.RideDTO;

import java.util.List;

public class DiskRideDBAdapter extends DiskDatabaseImpl<RideDTO> {

    public DiskRideDBAdapter() {
        super("hexagonal/persistence/disk/rides.json", RideDTO::id, new TypeToken<List<RideDTO>>() {}.getType());
    }
}
