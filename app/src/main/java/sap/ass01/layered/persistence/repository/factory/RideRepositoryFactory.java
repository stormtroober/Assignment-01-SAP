package sap.ass01.layered.persistence.repository.factory;

import sap.ass01.layered.persistence.MongoDB.MongoEBikeRepository;
import sap.ass01.layered.persistence.MongoDB.MongoRideRepository;
import sap.ass01.layered.persistence.inMemory.InMemoryEBikeRepository;
import sap.ass01.layered.persistence.inMemory.InMemoryRideRepository;
import sap.ass01.layered.persistence.mysql.MySQLRideRepository;
import sap.ass01.layered.persistence.mysql.MySqlEBikeRepository;
import sap.ass01.layered.persistence.repository.DatabaseType;
import sap.ass01.layered.persistence.repository.EBikeRepository;
import sap.ass01.layered.persistence.repository.RideRepository;

public class RideRepositoryFactory {
    public static RideRepository createRepository(DatabaseType dbType) {
        switch (dbType) {
            case IN_MEMORY:
                return new InMemoryRideRepository();
            case MONGODB:
                return new MongoRideRepository();
            case MYSQL:
                return new MySQLRideRepository();
            default:
                throw new UnsupportedOperationException("Database type not supported");
        }
    }
}
