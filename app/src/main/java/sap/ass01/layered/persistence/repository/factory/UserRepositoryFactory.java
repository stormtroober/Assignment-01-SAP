package sap.ass01.layered.persistence.repository.factory;

import sap.ass01.layered.persistence.inMemory.InMemoryUserRepository;
import sap.ass01.layered.persistence.repository.DatabaseType;
import sap.ass01.layered.persistence.repository.UserRepository;

public class UserRepositoryFactory {
    public static UserRepository createRepository(DatabaseType dbType) {
        switch (dbType) {
            case IN_MEMORY:
                return new InMemoryUserRepository();
            default:
                throw new UnsupportedOperationException("Database type not supported");
        }
    }
}