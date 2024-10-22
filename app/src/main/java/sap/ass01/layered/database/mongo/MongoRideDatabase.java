package sap.ass01.layered.database.mongo;

import org.bson.Document;
import sap.ass01.layered.database.implementation.MongoDatabaseImpl;
import sap.ass01.layered.database.dto.*;

import java.util.Optional;

public class MongoRideDatabase extends MongoDatabaseImpl<RideDTO> {

    public MongoRideDatabase() {
        super("ebikeDB", "rides", MongoRideDatabase::rideToDocument, MongoRideDatabase::documentToRideDTO);
    }

    private static Document rideToDocument(RideDTO ride) {
        return new Document("id", ride.id())
                .append("userId", ride.user().id())
                .append("userCredit", ride.user().credit())
                .append("userPermission", ride.user().permission())
                .append("ebikeId", ride.bike().id())
                .append("ebikeState", ride.bike().state())
                .append("ebikeX", ride.bike().x())
                .append("ebikeY", ride.bike().y())
                .append("ebikeDirectionX", ride.bike().directionX())
                .append("ebikeDirectionY", ride.bike().directionY())
                .append("ebikeSpeed", ride.bike().speed())
                .append("ebikeBatteryLevel", ride.bike().batteryLevel())
                .append("startedDate", ride.startedDate())
                .append("endDate", ride.endDate().orElse(null));
    }

    private static RideDTO documentToRideDTO(Document doc) {
        return new RideDTO(
                doc.getString("id"),
                new UserDTO(doc.getString("userId"), doc.getInteger("userCredit"), doc.getString("userPermission")),
                new EBikeDTO(doc.getString("ebikeId"), doc.getString("ebikeState"), doc.getDouble("ebikeX"), doc.getDouble("ebikeY"), doc.getDouble("ebikeDirectionX"), doc.getDouble("ebikeDirectionY"), doc.getDouble("ebikeSpeed"), doc.getInteger("ebikeBatteryLevel")),
                doc.getDate("startedDate"),
                Optional.ofNullable(doc.getDate("endDate"))
        );
    }
}