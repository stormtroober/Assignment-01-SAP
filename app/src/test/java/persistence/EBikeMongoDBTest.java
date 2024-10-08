package persistence;

import org.junit.jupiter.api.AfterEach;
import sap.ass01.layered.persistence.MongoDB.MongoEBikeRepository;
import sap.ass01.layered.persistence.repository.EBikeRepository;

public class EBikeMongoDBTest extends AbstractEBikeRepositoryTest{

    private MongoEBikeRepository repository;

    @Override
    protected EBikeRepository createRepository() {
        repository = new MongoEBikeRepository();
        return repository;
    }

    @AfterEach
    public void cleanDatabase() {
        repository.cleanDatabase();
    }
}
