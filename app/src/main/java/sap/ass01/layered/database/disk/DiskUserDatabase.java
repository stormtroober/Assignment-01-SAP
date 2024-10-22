package sap.ass01.layered.database.disk;

import com.google.gson.reflect.TypeToken;
import sap.ass01.layered.database.implementation.DiskDatabaseImpl;
import sap.ass01.layered.database.dto.UserDTO;

import java.util.List;

public class DiskUserDatabase extends DiskDatabaseImpl<UserDTO> {

    public DiskUserDatabase() {
        super("layered/persistence/disk/users.json", UserDTO::id, new TypeToken<List<UserDTO>>() {}.getType());
    }
}