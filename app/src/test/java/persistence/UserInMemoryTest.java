// File: app/src/test/java/persistence/UserInMemoryTest.java
package persistence;

import org.junit.jupiter.api.AfterEach;
import sap.ass01.layered.persistence.inMemory.InMemoryUserRepository;
import sap.ass01.layered.persistence.repository.UserRepository;

import java.io.File;

public class UserInMemoryTest extends AbstractUserRepositoryTest {

    private File saveFile = new File("persistence/in_memory/users.json");

    @Override
    protected UserRepository createRepository() {
        return new InMemoryUserRepository();
    }

    @AfterEach
    public void cleanDatabase() {
        if (saveFile.exists()) {
            saveFile.delete();
        }
    }
}