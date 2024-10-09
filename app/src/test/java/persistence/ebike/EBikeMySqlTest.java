package persistence.ebike;

import org.junit.jupiter.api.AfterEach;
import sap.ass01.layered.persistence.ebike.MySqlEBikeRepository;
import sap.ass01.layered.persistence.repository.EBikeRepository;

public class EBikeMySqlTest extends AbstractEBikeRepositoryTest{

    private MySqlEBikeRepository repository;

    @Override
    protected EBikeRepository createRepository() {
        repository = new MySqlEBikeRepository();
        return repository;
    }

    @AfterEach
    public void cleanDatabase() {
        repository.cleanDatabase();
    }
}
