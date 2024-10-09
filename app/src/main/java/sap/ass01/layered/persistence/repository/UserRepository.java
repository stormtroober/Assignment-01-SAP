package sap.ass01.layered.persistence.repository;

import sap.ass01.layered.persistence.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<UserDTO> findUserById(String userId);
    List<UserDTO> findAllUsers();
    void saveUser(UserDTO user);
    void updateUser(UserDTO user);
    void cleanDatabase();

}