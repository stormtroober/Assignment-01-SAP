package sap.ass01.layered.database.mongo;

import org.bson.Document;
import sap.ass01.layered.database.implementation.MongoDatabaseImpl;
import sap.ass01.layered.database.dto.EBikeDTO;

public class MongoEBikeDatabase extends MongoDatabaseImpl<EBikeDTO> {

    public MongoEBikeDatabase() {
        super("ebikeDB", "ebikes", MongoEBikeDatabase::ebikeToDocument, MongoEBikeDatabase::documentToEBikeDTO);
    }

    private static Document ebikeToDocument(EBikeDTO ebike) {
        return new Document("id", ebike.id())
                .append("state", ebike.state())
                .append("x", ebike.x())
                .append("y", ebike.y())
                .append("directionX", ebike.directionX())
                .append("directionY", ebike.directionY())
                .append("speed", ebike.speed())
                .append("batteryLevel", ebike.batteryLevel());
    }

    private static EBikeDTO documentToEBikeDTO(Document doc) {
        return new EBikeDTO(
                doc.getString("id"),
                doc.getString("state"),
                doc.getDouble("x"),
                doc.getDouble("y"),
                doc.getDouble("directionX"),
                doc.getDouble("directionY"),
                doc.getDouble("speed"),
                doc.getInteger("batteryLevel")
        );
    }
}