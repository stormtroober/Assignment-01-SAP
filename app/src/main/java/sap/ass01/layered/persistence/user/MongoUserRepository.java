package sap.ass01.layered.persistence.user;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;
import sap.ass01.layered.persistence.dto.UserDTO;
import sap.ass01.layered.persistence.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class MongoUserRepository implements UserRepository {

    private final MongoCollection<Document> collection;

    public MongoUserRepository() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("ebikeDB");
        collection = database.getCollection("users");
        collection.createIndex(new Document("id", 1), new IndexOptions().unique(true));
    }

    @Override
    public Optional<UserDTO> findUserById(String userId) {
        Document doc = collection.find(eq("id", userId)).first();
        return Optional.ofNullable(doc).map(this::documentToUserDTO);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<UserDTO> users = new ArrayList<>();
        for (Document doc : collection.find()) {
            users.add(documentToUserDTO(doc));
        }
        return users;
    }

    @Override
    public void saveUser(UserDTO user) {
        Document doc = userToDocument(user);
        try {
            collection.insertOne(doc);
        } catch (MongoWriteException e) {
            System.out.println("Trying to put a duplicate user in the database");
        }
    }

    @Override
    public void updateUser(UserDTO user) {
        Document doc = userToDocument(user);
        collection.replaceOne(eq("id", user.getId()), doc);
    }

    @Override
    public void cleanDatabase() {
        collection.drop();
    }

    private UserDTO documentToUserDTO(Document doc) {
        return new UserDTO(
                doc.getString("id"),
                doc.getInteger("credit"),
                doc.getString("permission")
        );
    }

    private Document userToDocument(UserDTO user) {
        return new Document("id", user.getId())
                .append("credit", user.getCredit())
                .append("permission", user.getPermission());
    }
}