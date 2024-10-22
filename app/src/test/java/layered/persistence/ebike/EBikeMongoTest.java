// EBikeMongoTest.java
package layered.persistence.ebike;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import sap.ass01.layered.config.DatabaseConfiguration;
import sap.ass01.layered.config.DatabaseType;
import sap.ass01.layered.persistence.repository.EBikeRepository;
import sap.ass01.layered.persistence.repository.EBikeRepositoryImpl;

public class EBikeMongoTest extends AbstractEBikeRepositoryTest {

    @BeforeAll
    public static void setUpOnce() {
        // Set the database type directly for testing
        DatabaseConfiguration.setDatabaseType(DatabaseType.MONGODB);
    }

    @BeforeEach
    public void setUp() {
        // Initialize the repository
        ebikeRepository = createRepository();
    }

    @Override
    protected EBikeRepository createRepository() {
        return new EBikeRepositoryImpl();
    }

    @AfterEach
    public void cleanDatabase() {
        ebikeRepository.cleanDatabase();
    }
}