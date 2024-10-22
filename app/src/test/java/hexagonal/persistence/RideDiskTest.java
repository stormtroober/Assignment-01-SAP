package hexagonal.persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import sap.ass01.hexagonal.application.ports.EBikeRepository;
import sap.ass01.hexagonal.application.ports.RideRepository;
import sap.ass01.hexagonal.infrastructure.adapters.infrastructure.db.disk.DiskRideDBAdapter;
import sap.ass01.hexagonal.infrastructure.adapters.infrastructure.persistence.DiskEBikeRepositoryImpl;
import sap.ass01.hexagonal.infrastructure.adapters.infrastructure.persistence.DiskRideRepositoryImpl;

public class RideDiskTest extends AbstractRideRepositoryTest {

    @BeforeEach
    public void setUp() {
        // Initialize the repository
        rideRepository = createRepository();
    }

    @Override
    protected RideRepository createRepository() {
        return new DiskRideRepositoryImpl();
    }

    @AfterEach
    public void cleanDatabase() {
        rideRepository.clean();
    }
}