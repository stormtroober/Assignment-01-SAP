// UserDiskTest.java
package hexagonal.persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import sap.ass01.hexagonal.application.ports.UserRepository;
import sap.ass01.hexagonal.infrastructure.adapters.persistence.DiskUserRepositoryImpl;

public class UserDiskTest extends AbstractUserRepositoryTest {

    @BeforeEach
    public void setUp() {
        userRepository = createRepository();
    }

    @Override
    protected UserRepository createRepository() {
        return new DiskUserRepositoryImpl();
    }

    @AfterEach
    public void cleanDatabase() {
        userRepository.clean();
    }
}