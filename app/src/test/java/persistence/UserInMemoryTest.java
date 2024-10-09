// File: app/src/test/java/persistence/UserInMemoryTest.java
package persistence;

import org.junit.jupiter.api.AfterEach;
import sap.ass01.layered.persistence.inMemory.InMemoryUserRepository;
import sap.ass01.layered.persistence.repository.UserRepository;
import sap.ass01.layered.persistence.repository.factory.UserRepositoryFactory;

import java.io.File;

import static sap.ass01.layered.persistence.repository.DatabaseType.IN_MEMORY;

public class UserInMemoryTest extends AbstractUserRepositoryTest {

    private UserRepository repository;

    @Override
    protected UserRepository createRepository() {
        repository = UserRepositoryFactory.createRepository(IN_MEMORY);
        return repository;
    }

    @AfterEach
    public void cleanDatabase() {
        repository.cleanDatabase();
    }
}