package sap.ass01.layered.persistence.inMemory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import sap.ass01.layered.persistence.DTO.UserDTO;
import sap.ass01.layered.persistence.Repositories.UserRepository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepository {

    private final Gson gson = new Gson();

    @Override
    public Optional<UserDTO> findUserById(String userId) {
        if(SAVE_FILE.exists()){
            List<UserDTO> users = readUsersFromFile();
            return users.stream()
                    .filter(user -> Objects.equals(user.getId(), userId))
                    .findFirst();
        }
        return Optional.empty();
    }

    @Override
    public List<UserDTO> findAllUsers() {
        return readUsersFromFile();
    }

    @Override
    public void updateUser(UserDTO user) {
        if (SAVE_FILE.exists()) {
            List<UserDTO> users = readUsersFromFile();
            for (int i = 0; i < users.size(); i++) {
                if (Objects.equals(users.get(i).getId(), user.getId())) {
                    users.set(i, user);
                    writeUsersToFile(users);
                    return;
                }
            }
        }
    }

    @Override
    public File getSaveFile() {
        return SAVE_FILE;
    }

    @Override
    public void saveUser(UserDTO user) {
        if(SAVE_FILE.exists()){
            List<UserDTO> users = readUsersFromFile();
            boolean userExists = users.stream()
                    .anyMatch(existingUser -> Objects.equals(existingUser.getId(), user.getId()));

            if (!userExists) {
                users.add(user);
                writeUsersToFile(users);
            }
        }
        else{
            List<UserDTO> users = new ArrayList<>();
            users.add(user);
            writeUsersToFile(users);
        }
    }

    private List<UserDTO> readUsersFromFile() {
        try (FileReader reader = new FileReader(SAVE_FILE)) {
            Type userListType = new TypeToken<ArrayList<UserDTO>>() {}.getType();
            List<UserDTO> users;

            // Read the JSON content as a generic Object
            Object jsonContent = gson.fromJson(reader, Object.class);

            // Check if the content is an array or a single object
            if (jsonContent instanceof List) {
                users = gson.fromJson(gson.toJson(jsonContent), userListType);
            } else {
                UserDTO singleUser = gson.fromJson(gson.toJson(jsonContent), UserDTO.class);
                users = new ArrayList<>();
                users.add(singleUser);
            }

            return users;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void writeUsersToFile(List<UserDTO> users) {
        try (FileWriter writer = new FileWriter(SAVE_FILE)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}