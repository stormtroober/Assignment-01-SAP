package hexagonal.persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import sap.ass01.hexagonal.application.ports.EBikeRepository;
import sap.ass01.hexagonal.infrastructure.adapters.infrastructure.persistence.DiskEBikeRepositoryImpl;
import sap.ass01.layered.config.DatabaseConfiguration;
import sap.ass01.layered.config.DatabaseType;

public class EBikeDiskTest extends AbstractEBikeRepositoryTest {

    @BeforeEach
    public void setUp() {
        // Initialize the repository
        ebikeRepository = createRepository();
    }

    @Override
    protected EBikeRepository createRepository() {
        return new DiskEBikeRepositoryImpl();
    }

    @AfterEach
    public void cleanDatabase() {
        ebikeRepository.clean();
    }
}