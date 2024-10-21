package sap.ass01.layered.database;

import sap.ass01.layered.config.DatabaseConfiguration;
import sap.ass01.layered.config.DatabaseType;
import sap.ass01.layered.database.disk.DiskEBikeDatabase;
import sap.ass01.layered.database.dto.EBikeDTO;
import sap.ass01.layered.database.mongo.MongoEBikeDatabase;

public class EBikeDatabaseFactory {
    public static Database<EBikeDTO> createDatabase() {
        DatabaseType dbType = DatabaseConfiguration.getDatabaseType();
        switch (dbType) {
            case DISK:
                return new DiskEBikeDatabase();
//            case MYSQL:
//                return new MySqlEBikeRepository();
            case MONGODB:
                return new MongoEBikeDatabase();
            default:
                throw new IllegalArgumentException("Unknown database type: " + dbType);
        }
    }
}