package sap.ass01.layered.persistence.repository.factory;

import sap.ass01.layered.persistence.MongoDB.MongoUserRepository;
import sap.ass01.layered.persistence.inMemory.InMemoryUserRepository;
import sap.ass01.layered.persistence.mysql.MySQLUserRepository;
import sap.ass01.layered.persistence.repository.DatabaseType;
import sap.ass01.layered.persistence.repository.UserRepository;

public class UserRepositoryFactory {
    public static UserRepository createRepository(DatabaseType dbType) {
        switch (dbType) {
            case IN_MEMORY:
                return new InMemoryUserRepository();
            case MONGODB:
                return new MongoUserRepository();
            case MYSQL:
                return new MySQLUserRepository();
            default:
                throw new UnsupportedOperationException("Database type not supported");
        }
    }
}