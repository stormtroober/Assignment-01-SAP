package sap.ass01.layered.persistence.repository;

import sap.ass01.layered.persistence.DTO.UserDTO;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    File SAVE_FILE = new File("persistence/in_memory/users.json");

    Optional<UserDTO> findUserById(String userId);
    List<UserDTO> findAllUsers();
    void saveUser(UserDTO user);
    void updateUser(UserDTO user);
    File getSaveFile();
}