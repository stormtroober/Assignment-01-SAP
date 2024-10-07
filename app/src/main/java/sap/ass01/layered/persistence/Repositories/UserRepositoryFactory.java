package sap.ass01.layered.persistence.Repositories;

import sap.ass01.layered.persistence.inMemory.InMemoryUserRepository;

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