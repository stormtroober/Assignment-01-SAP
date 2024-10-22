package layered.persistence.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import sap.ass01.layered.config.DatabaseConfiguration;
import sap.ass01.layered.config.DatabaseType;
import sap.ass01.layered.persistence.repository.UserRepositoryImpl;
import sap.ass01.layered.persistence.repository.UserRepository;

public class UserMySqlTest extends AbstractUserRepositoryTest {

    @BeforeAll
    public static void setUpOnce() {
        DatabaseConfiguration.setDatabaseType(DatabaseType.MYSQL);
    }

    @BeforeEach
    public void setUp() {
        // Initialize the repository
        userRepository = createRepository();
    }

    @Override
    protected UserRepository createRepository() {
        return new UserRepositoryImpl();
    }

    @AfterEach
    public void cleanDatabase() {
        userRepository.cleanDatabase();
    }
}