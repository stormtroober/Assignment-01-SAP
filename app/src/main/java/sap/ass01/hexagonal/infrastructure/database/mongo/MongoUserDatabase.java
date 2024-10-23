package sap.ass01.hexagonal.infrastructure.database.mongo;

import org.bson.Document;
import sap.ass01.hexagonal.application.ports.entities.UserDTO;
import sap.ass01.hexagonal.infrastructure.database.Database;

public class MongoUserDatabase extends MongoDatabaseImpl<UserDTO> implements Database<UserDTO> {

    public MongoUserDatabase() {
        super("ebikeDB", "users", MongoUserDatabase::userToDocument, MongoUserDatabase::documentToUserDTO);
    }

    private static Document userToDocument(UserDTO user) {
        return new Document("id", user.id())
                .append("credit", user.credit())
                .append("admin", user.admin());
    }

    private static UserDTO documentToUserDTO(Document doc) {
        return new UserDTO(
                doc.getString("id"),
                doc.getInteger("credit"),
                doc.getBoolean("admin")
        );
    }
}