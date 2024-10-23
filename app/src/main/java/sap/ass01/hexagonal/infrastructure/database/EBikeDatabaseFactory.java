package sap.ass01.hexagonal.infrastructure.database;

import sap.ass01.hexagonal.application.ports.entities.EBikeDTO;
import sap.ass01.hexagonal.infrastructure.adapters.persistence.DatabaseType;
import sap.ass01.hexagonal.infrastructure.database.diskdb.DiskEBikeDatabase;
import sap.ass01.hexagonal.infrastructure.database.mongo.MongoEBikeDatabase;
import sap.ass01.hexagonal.infrastructure.database.mysql.MySqlEBikeDatabase;
// import other database implementations as needed

public class EBikeDatabaseFactory {

    public static Database<EBikeDTO> getDatabase(DatabaseType databaseType) {
        switch (databaseType) {
            case DISK:
                return new DiskEBikeDatabase();
             case MYSQL:
                 return new MySqlEBikeDatabase();
             case MONGODB:
                 return new MongoEBikeDatabase();
            default:
                throw new IllegalArgumentException("Unsupported database type: " + databaseType);
        }
    }
}