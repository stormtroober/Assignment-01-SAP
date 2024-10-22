package layered.persistence.ride;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import sap.ass01.layered.config.DatabaseConfiguration;
import sap.ass01.layered.config.DatabaseType;
import sap.ass01.layered.persistence.repository.RideRepositoryImpl;
import sap.ass01.layered.persistence.repository.RideRepository;

public class RideDiskTest extends AbstractRideRepositoryTest {

    @BeforeAll
    public static void setUpOnce() {
        DatabaseConfiguration.setDatabaseType(DatabaseType.DISK);
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