// DiskRideDatabase.java
package sap.ass01.layered.database.disk;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import sap.ass01.layered.database.implementation.DiskDatabaseImpl;
import sap.ass01.layered.database.dto.RideDTO;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DiskRideDatabase extends DiskDatabaseImpl<RideDTO> {

    public DiskRideDatabase() {
        super("layered/persistence/disk/rides.json", RideDTO::id, new TypeToken<List<RideDTO>>() {}.getType());
    }

    @Override
    protected Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(new TypeToken<Optional<Date>>() {}.getType(), new OptionalTypeAdapter<Date>())
                .create();
    }
}