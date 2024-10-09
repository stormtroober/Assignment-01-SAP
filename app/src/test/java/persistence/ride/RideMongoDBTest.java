package persistence.ride;

import org.junit.jupiter.api.AfterEach;
import sap.ass01.layered.persistence.repository.RideRepository;
import sap.ass01.layered.persistence.repository.factory.RideRepositoryFactory;

import static sap.ass01.layered.persistence.repository.DatabaseType.IN_MEMORY;
import static sap.ass01.layered.persistence.repository.DatabaseType.MONGODB;

public class RideMongoDBTest extends AbstractRideRepositoryTest{

    private RideRepository repository;

    @Override
    protected RideRepository createRepository() {
        repository = RideRepositoryFactory.createRepository(MONGODB);
        return repository;
    }

    @AfterEach
    public void cleanDatabase() {
        repository.cleanDatabase();
    }
}
