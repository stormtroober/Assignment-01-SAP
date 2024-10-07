package sap.ass01.layered.persistence.Repositories;
import sap.ass01.layered.persistence.UserDTOTemp;

import java.util.List;

public interface UserRepository {
    UserDTOTemp findUserById(int userId);
    List<UserDTOTemp> findAllUsers();
    void saveUser(UserDTOTemp user);
    void updateUser(UserDTOTemp user);
}
