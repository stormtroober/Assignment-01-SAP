package sap.ass01.layered.persistence.inMemory;

//import sap.ass01.layered.domain.User;
import sap.ass01.layered.persistence.Repositories.UserRepository;
import sap.ass01.layered.persistence.UserDTOTemp;

import java.util.List;

public class InMemoryUserRepository implements UserRepository {
    @Override
    public UserDTOTemp findUserById(int userId) {
        return null;
    }

    @Override
    public List<UserDTOTemp> findAllUsers() {
        return List.of();
    }

    @Override
    public void saveUser(UserDTOTemp user) {

    }

    @Override
    public void updateUser(UserDTOTemp user) {

    }
}
