package sap.ass01.layered.database;

import sap.ass01.layered.config.DatabaseConfiguration;
import sap.ass01.layered.config.DatabaseType;
import sap.ass01.layered.database.disk.DiskRideDatabase;
import sap.ass01.layered.database.mongo.MongoRideDatabase;
import sap.ass01.layered.database.dto.RideDTO;

public class RideDatabaseFactory {
    public static Database<RideDTO> createDatabase() {
        DatabaseType dbType = DatabaseConfiguration.getDatabaseType();
        switch (dbType) {
            case DISK:
                return new DiskRideDatabase();
//            case MYSQL:
//                return new MySqlEBikeRepository();
            case MONGODB:
                return new MongoRideDatabase();
            default:
                throw new IllegalArgumentException("Unknown database type: " + dbType);
        }
    }
}
