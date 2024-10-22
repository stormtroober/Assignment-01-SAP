package sap.ass01.hexagonal.application.ports;

import sap.ass01.hexagonal.application.entities.EBikeDTO;
import sap.ass01.hexagonal.application.entities.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<UserDTO> findUserById(String userId);
    List<UserDTO> findAllUsers();
    void saveUser(UserDTO user);
    void updateUser(UserDTO user);
    void clean();
}
