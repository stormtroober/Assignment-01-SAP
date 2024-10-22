// DiskUserRepositoryImpl.java
package sap.ass01.hexagonal.infrastructure.adapters.infrastructure.persistence;

import sap.ass01.hexagonal.application.entities.UserDTO;
import sap.ass01.hexagonal.application.ports.UserRepository;
import sap.ass01.hexagonal.infrastructure.adapters.infrastructure.db.disk.DiskUserDBAdapter;

import java.util.List;
import java.util.Optional;

public class DiskUserRepositoryImpl implements UserRepository {

    private final DiskUserDBAdapter diskUserDatabase;

    public DiskUserRepositoryImpl() {
        this.diskUserDatabase = new DiskUserDBAdapter();
    }

    @Override
    public Optional<UserDTO> findUserById(String userId) {
        return diskUserDatabase.findById(userId);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        return diskUserDatabase.findAll();
    }

    @Override
    public void saveUser(UserDTO user) {
        diskUserDatabase.save(user);
    }

    @Override
    public void updateUser(UserDTO user) {
        diskUserDatabase.update(user);
    }

    @Override
    public void clean() {
        diskUserDatabase.clean();
    }
}