// File: app/src/test/java/persistence/AbstractUserRepositoryTest.java
package persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sap.ass01.layered.persistence.DTO.UserDTO;
import sap.ass01.layered.persistence.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractUserRepositoryTest {

    protected UserRepository userRepository;

    protected abstract UserRepository createRepository();

    @BeforeEach
    public void setUp() {
        userRepository = createRepository();
    }

    @AfterEach
    public void cleanDatabase() {
        // Implement in subclasses
    }

    @Test
    public void testSaveUser() {
        UserDTO user = new UserDTO("1", 100, "USER");
        userRepository.saveUser(user);
        Optional<UserDTO> retrievedUserOptional = userRepository.findUserById("1");
        assertTrue(retrievedUserOptional.isPresent(), "User should be found by ID");
        UserDTO retrievedUser = retrievedUserOptional.get();
        assertEquals("1", retrievedUser.getId(), "User ID should match");
        assertEquals(100, retrievedUser.getCredit(), "User credit should match");
        assertEquals("USER", retrievedUser.getPermission(), "User permission should match");
    }

    @Test
    public void testSaveDuplicateUser() {
        UserDTO user1 = new UserDTO("1", 100, "USER");
        userRepository.saveUser(user1);
        UserDTO user2 = new UserDTO("1", 200, "ADMIN");
        userRepository.saveUser(user2);

        List<UserDTO> users = userRepository.findAllUsers();
        assertEquals(1, users.size(), "There should be only one user in the repository");
        assertEquals("1", users.get(0).getId(), "User ID should match");
        assertEquals(100, users.get(0).getCredit(), "User credit should match the first user");
        assertEquals("USER", users.get(0).getPermission(), "User permission should match the first user");
    }

    @Test
    public void testFindUserById() {
        UserDTO user = new UserDTO("1", 100, "USER");
        userRepository.saveUser(user);
        Optional<UserDTO> retrievedUserOptional = userRepository.findUserById("1");
        assertTrue(retrievedUserOptional.isPresent(), "User should be found by ID");
        UserDTO retrievedUser = retrievedUserOptional.get();
        assertEquals("1", retrievedUser.getId(), "User ID should match");
        assertEquals(100, retrievedUser.getCredit(), "User credit should match");
        assertEquals("USER", retrievedUser.getPermission(), "User permission should match");
    }

    @Test
    public void testFindAllUsers() {
        UserDTO user1 = new UserDTO("1", 100, "USER");
        UserDTO user2 = new UserDTO("2", 200, "ADMIN");
        userRepository.saveUser(user1);
        userRepository.saveUser(user2);

        List<UserDTO> users = userRepository.findAllUsers();
        assertEquals(2, users.size(), "There should be two users in the repository");
        assertTrue(users.stream().anyMatch(user -> user.getId().equals("1")), "User with ID 1 should be present");
        assertTrue(users.stream().anyMatch(user -> user.getId().equals("2")), "User with ID 2 should be present");
    }

    @Test
    public void testUpdateUser() {
        UserDTO user = new UserDTO("1", 100, "USER");
        userRepository.saveUser(user);

        UserDTO updatedUser = new UserDTO("1", 200, "ADMIN");
        userRepository.updateUser(updatedUser);

        Optional<UserDTO> retrievedUserOptional = userRepository.findUserById("1");
        assertTrue(retrievedUserOptional.isPresent(), "User should be found by ID");
        UserDTO retrievedUser = retrievedUserOptional.get();
        assertEquals("1", retrievedUser.getId(), "User ID should match");
        assertEquals(200, retrievedUser.getCredit(), "User credit should match updated value");
        assertEquals("ADMIN", retrievedUser.getPermission(), "User permission should match updated value");
    }
}