package sap.ass01.layered.database.disk;

import com.google.gson.reflect.TypeToken;
import sap.ass01.layered.database.implementation.DiskDatabaseImpl;
import sap.ass01.layered.database.dto.EBikeDTO;

import java.util.List;

public class DiskEBikeDatabase extends DiskDatabaseImpl<EBikeDTO> {

    public DiskEBikeDatabase() {
        super("persistence/in_memory/ebikes.json", EBikeDTO::id, new TypeToken<List<EBikeDTO>>() {}.getType());
    }
}