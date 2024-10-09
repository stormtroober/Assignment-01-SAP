package persistence.ride;

import org.junit.jupiter.api.AfterEach;
import sap.ass01.layered.persistence.repository.EBikeRepository;
import sap.ass01.layered.persistence.repository.RideRepository;
import sap.ass01.layered.persistence.repository.factory.EBikeRepositoryFactory;
import sap.ass01.layered.persistence.repository.factory.RideRepositoryFactory;

import static sap.ass01.layered.persistence.repository.DatabaseType.IN_MEMORY;

public class RideInMemoryTest extends AbstractRideRepositoryTest {

    private RideRepository repository;

    @Override
    protected RideRepository createRepository() {
        repository = RideRepositoryFactory.createRepository(IN_MEMORY);
        return repository;
    }

    @AfterEach
    public void cleanDatabase() {
        repository.cleanDatabase();
    }
}
