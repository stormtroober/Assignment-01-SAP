package sap.ass01.layered.database;

import sap.ass01.layered.config.DatabaseConfiguration;
import sap.ass01.layered.config.DatabaseType;
import sap.ass01.layered.database.disk.DiskUserDatabase;
import sap.ass01.layered.database.mongo.MongoUserDatabase;
import sap.ass01.layered.database.dto.UserDTO;
import sap.ass01.layered.database.mysql.MySqlUserDatabase;

public class UserDatabaseFactory {
    public static Database<UserDTO> createDatabase() {
        DatabaseType dbType = DatabaseConfiguration.getDatabaseType();
        switch (dbType) {
            case DISK:
                return new DiskUserDatabase();
            case MYSQL:
                return new MySqlUserDatabase();
            case MONGODB:
                return new MongoUserDatabase();
            default:
                throw new IllegalArgumentException("Unknown database type: " + dbType);
        }
    }
}