package sap.ass01.hexagonal.infrastructure.database;

import sap.ass01.hexagonal.application.ports.entities.RideDTO;
import sap.ass01.hexagonal.infrastructure.adapters.persistence.DatabaseType;
import sap.ass01.hexagonal.infrastructure.database.diskdb.DiskRideDatabase;
import sap.ass01.hexagonal.infrastructure.database.mongo.MongoRideDatabase;
// import other database implementations as needed

public class RideDatabaseFactory {

    public static Database<RideDTO> getDatabase(DatabaseType databaseType) {
        switch (databaseType) {
            case DISK:
                return new DiskRideDatabase();
            // case MYSQL:
            //     return new MySQLRideDatabase();
             case MONGODB:
                 return new MongoRideDatabase();
            default:
                throw new IllegalArgumentException("Unsupported database type: " + databaseType);
        }
    }
}