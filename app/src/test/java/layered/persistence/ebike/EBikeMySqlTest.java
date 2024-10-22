package layered.persistence.ebike;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import sap.ass01.layered.config.DatabaseConfiguration;
import sap.ass01.layered.config.DatabaseType;
import sap.ass01.layered.persistence.repository.EBikeRepository;
import sap.ass01.layered.persistence.repository.EBikeRepositoryImpl;

public class EBikeMySqlTest extends AbstractEBikeRepositoryTest {

    @BeforeAll
    public static void setUpOnce() {
        DatabaseConfiguration.setDatabaseType(DatabaseType.MYSQL);
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