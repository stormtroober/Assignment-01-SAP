package persistence.ride;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import sap.ass01.layered.config.DatabaseConfiguration;
import sap.ass01.layered.config.DatabaseType;
import sap.ass01.layered.persistence.RideRepositoryImpl;
import sap.ass01.layered.persistence.repository.RideRepository;

public class RideMongoTest extends AbstractRideRepositoryTest {

    @BeforeAll
    public static void setUpOnce() {
        DatabaseConfiguration.setDatabaseType(DatabaseType.MONGODB);
    }

    @BeforeEach
    public void setUp() {
        // Initialize the repository
        rideRepository = createRepository();
    }

    @Override
    protected RideRepository createRepository() {
        return new RideRepositoryImpl();
    }

    @AfterEach
    public void cleanDatabase() {
        rideRepository.cleanDatabase();
    }
}