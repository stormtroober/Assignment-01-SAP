package persistence.ebike;

import org.junit.jupiter.api.AfterEach;
import sap.ass01.layered.persistence.repository.EBikeRepository;
import sap.ass01.layered.persistence.repository.factory.EBikeRepositoryFactory;

import static sap.ass01.layered.persistence.repository.DatabaseType.MONGODB;

public class EBikeMongoDBTest extends AbstractEBikeRepositoryTest{

    private EBikeRepository repository;

    @Override
    protected EBikeRepository createRepository() {
        repository = EBikeRepositoryFactory.createRepository(MONGODB);
        return repository;
    }

    @AfterEach
    public void cleanDatabase() {
        repository.cleanDatabase();
    }
}
