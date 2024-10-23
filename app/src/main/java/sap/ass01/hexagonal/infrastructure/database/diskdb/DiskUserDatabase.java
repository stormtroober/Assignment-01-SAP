package sap.ass01.hexagonal.infrastructure.database.diskdb;

import com.google.gson.reflect.TypeToken;
import sap.ass01.hexagonal.application.ports.entities.UserDTO;
import sap.ass01.hexagonal.infrastructure.database.Database;

import java.util.List;

public class DiskUserDatabase extends DiskDatabaseImpl<UserDTO> implements Database<UserDTO> {

    public DiskUserDatabase() {
        super("hexagonal/persistence/disk/users.json", UserDTO::id, new TypeToken<List<UserDTO>>() {}.getType());
    }
}
