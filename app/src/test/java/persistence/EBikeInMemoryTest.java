package persistence;

import org.junit.jupiter.api.AfterEach;
import sap.ass01.layered.persistence.inMemory.InMemoryEBikeRepository;
import sap.ass01.layered.persistence.repository.EBikeRepository;

import java.io.File;

public class EBikeInMemoryTest extends AbstractEBikeRepositoryTest {

    File saveFile = new File("persistence/in_memory/ebikes.json");

    @Override
    protected EBikeRepository createRepository() {
        return new InMemoryEBikeRepository();
    }

    @AfterEach
    public void cleanDatabase() {
        if (saveFile != null && saveFile.exists()) {
            saveFile.delete();
        }
    }
}