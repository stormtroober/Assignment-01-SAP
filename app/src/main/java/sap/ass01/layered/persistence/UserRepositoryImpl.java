package sap.ass01.layered.persistence;

import sap.ass01.layered.database.Database;
import sap.ass01.layered.database.UserDatabaseFactory;
import sap.ass01.layered.persistence.dto.UserDTO;
import sap.ass01.layered.persistence.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserRepositoryImpl implements UserRepository {

    private final Database<sap.ass01.layered.database.dto.UserDTO> database;

    public UserRepositoryImpl() {
        this.database = UserDatabaseFactory.createDatabase();
    }

    @Override
    public Optional<UserDTO> findUserById(String userId) {
        return database.findById(userId)
                .map(DatabaseToPersistenceMapper::toPersistenceDTO);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        return database.findAll().stream()
                .map(DatabaseToPersistenceMapper::toPersistenceDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void saveUser(UserDTO user) {
        database.save(PersistenceToDatabaseMapper.toDatabaseDTO(user));
    }

    @Override
    public void updateUser(UserDTO user) {
        database.update(PersistenceToDatabaseMapper.toDatabaseDTO(user));
    }

    @Override
    public void cleanDatabase() {
        database.clean();
    }
}