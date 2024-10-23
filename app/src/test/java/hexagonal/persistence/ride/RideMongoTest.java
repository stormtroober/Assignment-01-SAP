package hexagonal.persistence.ride;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import sap.ass01.hexagonal.application.ports.RideRepository;
import sap.ass01.hexagonal.infrastructure.adapters.persistence.DatabaseType;
import sap.ass01.hexagonal.infrastructure.adapters.persistence.RideRepositoryAdapter;

public class RideMongoTest extends AbstractRideRepositoryTest {

    @BeforeEach
    public void setUp() {
        // Initialize the repository
        rideRepository = createRepository();
    }

    @Override
    protected RideRepository createRepository() {
        return new RideRepositoryAdapter(DatabaseType.MONGODB);
    }

    @AfterEach
    public void cleanDatabase() {
        rideRepository.clean();
    }
}