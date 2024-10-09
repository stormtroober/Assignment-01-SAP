package persistence.user;

import org.junit.jupiter.api.AfterEach;
import sap.ass01.layered.persistence.repository.UserRepository;
import sap.ass01.layered.persistence.repository.factory.UserRepositoryFactory;

import static sap.ass01.layered.persistence.repository.DatabaseType.MONGODB;

public class UserMongoDBTest extends AbstractUserRepositoryTest{

    private UserRepository repository;

    @Override
    protected UserRepository createRepository() {
        repository = UserRepositoryFactory.createRepository(MONGODB);
        return repository;
    }

    @AfterEach
    public void cleanDatabase() {
        repository.cleanDatabase();
    }
}
