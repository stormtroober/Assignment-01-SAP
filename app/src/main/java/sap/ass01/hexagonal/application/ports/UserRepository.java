package sap.ass01.hexagonal.application.ports;

import sap.ass01.hexagonal.application.ports.entities.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
/**
 * Finds a user by their ID.
 *
 * @param userId the ID of the user to find.
 * @return an Optional containing the found user, or an empty Optional if no user is found.
 */
Optional<UserDTO> findUserById(String userId);

/**
 * Finds all users.
 *
 * @return a list of all users.
 */
List<UserDTO> findAllUsers();

/**
 * Saves a new user.
 *
 * @param user the user to save.
 */
void saveUser(UserDTO user);

/**
 * Updates an existing user.
 *
 * @param user the user to update.
 */
void updateUser(UserDTO user);

/**
 * Cleans the repository, removing all users.
 */
void clean();
}
