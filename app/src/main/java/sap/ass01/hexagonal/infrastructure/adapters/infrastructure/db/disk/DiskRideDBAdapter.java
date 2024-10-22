package sap.ass01.hexagonal.infrastructure.adapters.infrastructure.db.disk;

import com.google.gson.reflect.TypeToken;
import sap.ass01.hexagonal.application.entities.RideDTO;

import java.util.List;

public class DiskRideDBAdapter extends DiskDatabaseImpl<RideDTO> {

    public DiskRideDBAdapter() {
        super("hexagonal/persistence/disk/rides.json", RideDTO::id, new TypeToken<List<RideDTO>>() {}.getType());
    }
}
