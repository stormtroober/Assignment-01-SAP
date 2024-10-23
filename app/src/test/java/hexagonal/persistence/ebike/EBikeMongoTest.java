package hexagonal.persistence.ebike;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import sap.ass01.hexagonal.application.ports.EBikeRepository;
import sap.ass01.hexagonal.infrastructure.adapters.persistence.DatabaseType;
import sap.ass01.hexagonal.infrastructure.adapters.persistence.EBikeRepositoryAdapter;

public class EBikeMongoTest extends AbstractEBikeRepositoryTest{

    @BeforeEach
    public void setUp() {
        // Initialize the repository
        ebikeRepository = createRepository();
    }
    @Override
    protected EBikeRepository createRepository() {
        return new EBikeRepositoryAdapter(DatabaseType.MONGODB);
    }

    @AfterEach
    public void cleanDatabase() {
        ebikeRepository.clean();
    }
}
