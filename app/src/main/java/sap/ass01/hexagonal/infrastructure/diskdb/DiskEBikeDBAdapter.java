package sap.ass01.hexagonal.infrastructure.diskdb;

import com.google.gson.reflect.TypeToken;
import sap.ass01.hexagonal.application.ports.entities.EBikeDTO;
import sap.ass01.layered.database.implementation.DiskDatabaseImpl;

import java.util.List;

public class DiskEBikeDBAdapter extends DiskDatabaseImpl<EBikeDTO> {

    public DiskEBikeDBAdapter() {
        super("hexagonal/persistence/disk/ebikes.json", EBikeDTO::id, new TypeToken<List<EBikeDTO>>() {}.getType());
    }
}