package persistence;

import org.junit.jupiter.api.AfterEach;
import sap.ass01.layered.persistence.inMemory.InMemoryEBikeRepository;
import sap.ass01.layered.persistence.repository.EBikeRepository;
import sap.ass01.layered.persistence.repository.factory.EBikeRepositoryFactory;

import java.io.File;

import static sap.ass01.layered.persistence.repository.DatabaseType.IN_MEMORY;

public class EBikeInMemoryTest extends AbstractEBikeRepositoryTest {

    private EBikeRepository repository;

    @Override
    protected EBikeRepository createRepository() {
        repository = EBikeRepositoryFactory.createRepository(IN_MEMORY);
        return repository;
    }

    @AfterEach
    public void cleanDatabase() {
        repository.cleanDatabase();
    }
}