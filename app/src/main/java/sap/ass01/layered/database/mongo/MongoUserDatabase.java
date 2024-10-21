package sap.ass01.layered.database.mongo;

import org.bson.Document;
import sap.ass01.layered.database.implementation.MongoDatabaseImpl;
import sap.ass01.layered.database.dto.UserDTO;

public class MongoUserDatabase extends MongoDatabaseImpl<UserDTO> {

    public MongoUserDatabase() {
        super("ebikeDB", "users", MongoUserDatabase::userToDocument, MongoUserDatabase::documentToUserDTO);
    }

    private static Document userToDocument(UserDTO user) {
        return new Document("id", user.id())
                .append("credit", user.credit())
                .append("permission", user.permission());
    }

    private static UserDTO documentToUserDTO(Document doc) {
        return new UserDTO(
                doc.getString("id"),
                doc.getInteger("credit"),
                doc.getString("permission")
        );
    }
}