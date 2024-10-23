package hexagonal.persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sap.ass01.hexagonal.application.ports.entities.UserDTO;
import sap.ass01.hexagonal.application.ports.UserRepository;

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
        userRepository.clean();
    }

    @Test
    public void testSaveUser() {
        UserDTO user = new UserDTO("1", 100, false);
        userRepository.saveUser(user);
        Optional<UserDTO> retrievedUserOptional = userRepository.findUserById("1");
        assertTrue(retrievedUserOptional.isPresent(), "User should be found by ID");
        UserDTO retrievedUser = retrievedUserOptional.get();
        assertEquals("1", retrievedUser.id(), "User ID should match");
        assertEquals(100, retrievedUser.credit(), "User credit should match");
        assertFalse(retrievedUser.admin(), "User admin status should match");
    }

    @Test
    public void testSaveDuplicateUser() {
        UserDTO user1 = new UserDTO("1", 100, false);
        userRepository.saveUser(user1);
        UserDTO user2 = new UserDTO("1", 200, true);
        userRepository.saveUser(user2);

        List<UserDTO> users = userRepository.findAllUsers();
        assertEquals(1, users.size(), "There should be only one user in the repository");
        assertEquals("1", users.getFirst().id(), "User ID should match");
        assertEquals(100, users.getFirst().credit(), "User credit should match the first user");
        assertFalse(users.getFirst().admin(), "User admin status should match the first user");
    }

    @Test
    public void testFindUserById() {
        UserDTO user = new UserDTO("1", 100, false);
        userRepository.saveUser(user);
        Optional<UserDTO> retrievedUserOptional = userRepository.findUserById("1");
        assertTrue(retrievedUserOptional.isPresent(), "User should be found by ID");
        UserDTO retrievedUser = retrievedUserOptional.get();
        assertEquals("1", retrievedUser.id(), "User ID should match");
        assertEquals(100, retrievedUser.credit(), "User credit should match");
        assertFalse(retrievedUser.admin(), "User admin status should match");
    }

    @Test
    public void testFindAllUsers() {
        UserDTO user1 = new UserDTO("1", 100, false);
        UserDTO user2 = new UserDTO("2", 200, true);
        userRepository.saveUser(user1);
        userRepository.saveUser(user2);

        List<UserDTO> users = userRepository.findAllUsers();
        assertEquals(2, users.size(), "There should be two users in the repository");
        assertTrue(users.stream().anyMatch(user -> user.id().equals("1")), "User with ID 1 should be present");
        assertTrue(users.stream().anyMatch(user -> user.id().equals("2")), "User with ID 2 should be present");
    }

    @Test
    public void testUpdateUser() {
        UserDTO user = new UserDTO("1", 100, false);
        userRepository.saveUser(user);

        UserDTO updatedUser = new UserDTO("1", 200, true);
        userRepository.updateUser(updatedUser);

        Optional<UserDTO> retrievedUserOptional = userRepository.findUserById("1");
        assertTrue(retrievedUserOptional.isPresent(), "User should be found by ID");
        UserDTO retrievedUser = retrievedUserOptional.get();
        assertEquals("1", retrievedUser.id(), "User ID should match");
        assertEquals(200, retrievedUser.credit(), "User credit should match updated value");
        assertTrue(retrievedUser.admin(), "User admin status should match updated value");
    }
}