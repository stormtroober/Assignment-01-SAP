package sap.ass01.hexagonal.infrastructure.adapters.infrastructure.db.disk;

import com.google.gson.reflect.TypeToken;
import sap.ass01.hexagonal.application.entities.UserDTO;

import java.util.List;

public class DiskUserDBAdapter extends DiskDatabaseImpl<UserDTO> {

    public DiskUserDBAdapter() {
        super("hexagonal/persistence/disk/users.json", UserDTO::id, new TypeToken<List<UserDTO>>() {}.getType());
    }
}
