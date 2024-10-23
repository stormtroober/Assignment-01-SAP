package sap.ass01.hexagonal.infrastructure.database.mongo;

import org.bson.Document;
import sap.ass01.hexagonal.application.ports.entities.EBikeDTO;
import sap.ass01.hexagonal.infrastructure.database.Database;

import javax.xml.crypto.Data;

public class MongoEBikeDatabase extends MongoDatabaseImpl<EBikeDTO> implements Database<EBikeDTO> {

    public MongoEBikeDatabase() {
        super("ebikeDB", "ebikes", MongoEBikeDatabase::ebikeToDocument, MongoEBikeDatabase::documentToEBikeDTO);
    }

    private static Document ebikeToDocument(EBikeDTO ebike) {
        return new Document("id", ebike.id())
                .append("x", ebike.x())
                .append("y", ebike.y())
                .append("state", ebike.state())
                .append("battery", ebike.battery());
    }

    private static EBikeDTO documentToEBikeDTO(Document doc) {
        return new EBikeDTO(
                doc.getString("id"),
                doc.getDouble("x"),
                doc.getDouble("y"),
                doc.getString("state"),
                doc.getInteger("battery")
        );
    }
}