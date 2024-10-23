package sap.ass01.hexagonal.infrastructure.database.diskdb;

import com.google.gson.reflect.TypeToken;
import sap.ass01.hexagonal.application.ports.entities.EBikeDTO;
import sap.ass01.hexagonal.infrastructure.database.Database;
import sap.ass01.layered.database.implementation.DiskDatabaseImpl;

import java.util.List;

public class DiskEBikeDatabase extends DiskDatabaseImpl<EBikeDTO> implements Database<EBikeDTO> {

    public DiskEBikeDatabase() {
        super("hexagonal/persistence/disk/ebikes.json", EBikeDTO::id, new TypeToken<List<EBikeDTO>>() {}.getType());
    }
}