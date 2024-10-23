package sap.ass01.hexagonal.infrastructure.database;

import sap.ass01.hexagonal.application.ports.entities.UserDTO;
import sap.ass01.hexagonal.infrastructure.adapters.persistence.DatabaseType;
import sap.ass01.hexagonal.infrastructure.database.diskdb.DiskUserDatabase;
import sap.ass01.hexagonal.infrastructure.database.mongo.MongoUserDatabase;
// import other database implementations as needed

public class UserDatabaseFactory {

    public static Database<UserDTO> getDatabase(DatabaseType databaseType) {
        switch (databaseType) {
            case DISK:
                return new DiskUserDatabase();
            // case MYSQL:
            //     return new MySQLUserDatabase();
             case MONGODB:
                 return new MongoUserDatabase();
            default:
                throw new IllegalArgumentException("Unsupported database type: " + databaseType);
        }
    }
}