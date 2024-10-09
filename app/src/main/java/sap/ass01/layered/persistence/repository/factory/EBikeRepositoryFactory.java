package sap.ass01.layered.persistence.repository.factory;

import sap.ass01.layered.persistence.MongoDB.MongoEBikeRepository;
import sap.ass01.layered.persistence.inMemory.InMemoryEBikeRepository;
import sap.ass01.layered.persistence.mysql.MySqlEBikeRepository;
import sap.ass01.layered.persistence.repository.DatabaseType;
import sap.ass01.layered.persistence.repository.EBikeRepository;

public class EBikeRepositoryFactory {
    public static EBikeRepository createRepository(DatabaseType dbType) {
        switch (dbType) {
            case IN_MEMORY:
                return new InMemoryEBikeRepository();
            case MONGODB:
                return new MongoEBikeRepository();
            case MYSQL:
                return new MySqlEBikeRepository();
            default:
                throw new UnsupportedOperationException("Database type not supported");
        }
    }
}