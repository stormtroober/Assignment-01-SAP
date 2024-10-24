package sap.ass01.layered.persistence.repository;

import sap.ass01.layered.persistence.dto.UserDTO;

import java.util.List;
import java.util.Optional;

/**
 * Interface representing the repository for managing users.
 */
public interface UserRepository {

    /**
     * Finds a user by their ID.
     *
     * @param userId the ID of the user to be found
     * @return an Optional containing the found user, or empty if not found
     */
    Optional<UserDTO> findUserById(String userId);

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    List<UserDTO> findAllUsers();

    /**
     * Saves the given user to the repository.
     *
     * @param user the user to be saved
     */
    void saveUser(UserDTO user);

    /**
     * Updates the given user in the repository.
     *
     * @param user the user to be updated
     */
    void updateUser(UserDTO user);

    /**
     * Cleans the database, removing all users.
     */
    void cleanDatabase();
}