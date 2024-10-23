package sap.ass01.hexagonal.infrastructure.database.mongo;

import org.bson.Document;
import sap.ass01.hexagonal.application.ports.entities.EBikeDTO;
import sap.ass01.hexagonal.application.ports.entities.RideDTO;
import sap.ass01.hexagonal.application.ports.entities.UserDTO;
import sap.ass01.hexagonal.infrastructure.database.Database;

public class MongoRideDatabase extends MongoDatabaseImpl<RideDTO> implements Database<RideDTO> {

    public MongoRideDatabase() {
        super("ebikeDB", "rides", MongoRideDatabase::rideToDocument, MongoRideDatabase::documentToRideDTO);
    }

    private static Document rideToDocument(RideDTO ride) {
        return new Document("id", ride.id())
                .append("userId", ride.user().id())
                .append("userCredit", ride.user().credit())
                .append("userAdmin", ride.user().admin())
                .append("ebikeId", ride.ebike().id())
                .append("ebikeState", ride.ebike().state())
                .append("ebikeX", ride.ebike().x())
                .append("ebikeY", ride.ebike().y())
                .append("ebikeBattery", ride.ebike().battery());
    }

    private static RideDTO documentToRideDTO(Document doc) {
        return new RideDTO(
                doc.getString("id"),
                new EBikeDTO(
                        doc.getString("ebikeId"),
                        doc.getDouble("ebikeX"),
                        doc.getDouble("ebikeY"),
                        doc.getString("ebikeState"),
                        doc.getInteger("ebikeBattery")
                ),
                new UserDTO(
                        doc.getString("userId"),
                        doc.getInteger("userCredit"),
                        doc.getBoolean("userAdmin")
                )
        );
    }
}