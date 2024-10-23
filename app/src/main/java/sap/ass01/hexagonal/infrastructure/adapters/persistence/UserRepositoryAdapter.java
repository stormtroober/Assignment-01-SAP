package sap.ass01.hexagonal.infrastructure.adapters.persistence;

import sap.ass01.hexagonal.application.ports.UserRepository;
import sap.ass01.hexagonal.application.ports.entities.UserDTO;
import sap.ass01.hexagonal.infrastructure.database.Database;
import sap.ass01.hexagonal.infrastructure.database.UserDatabaseFactory;

import java.util.List;
import java.util.Optional;

public class UserRepositoryAdapter implements UserRepository {

    private final Database<UserDTO> userDatabase;

    public UserRepositoryAdapter(DatabaseType databaseType) {
        this.userDatabase = UserDatabaseFactory.getDatabase(databaseType);
    }

    @Override
    public Optional<UserDTO> findUserById(String userId) {
        return userDatabase.findById(userId);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        return userDatabase.findAll();
    }

    @Override
    public void saveUser(UserDTO user) {
        userDatabase.save(user);
    }

    @Override
    public void updateUser(UserDTO user) {
        userDatabase.update(user);
    }

    @Override
    public void clean() {
        userDatabase.clean();
    }
}