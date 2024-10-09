package sap.ass01.layered.persistence.ride;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;
import sap.ass01.layered.persistence.dto.EBikeDTO;
import sap.ass01.layered.persistence.dto.RideDTO;
import sap.ass01.layered.persistence.dto.UserDTO;
import sap.ass01.layered.persistence.repository.RideRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class MongoRideRepository implements RideRepository {

    private final MongoCollection<Document> collection;

    public MongoRideRepository() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("ebikeDB");
        collection = database.getCollection("rides");
        collection.createIndex(new Document("id", 1), new IndexOptions().unique(true));
    }

    @Override
    public Optional<RideDTO> findRideById(String rideId) {
        Document doc = collection.find(eq("id", rideId)).first();
        return Optional.ofNullable(doc).map(this::documentToRideDTO);
    }

    @Override
    public List<RideDTO> findAllRides() {
        List<RideDTO> rides = new ArrayList<>();
        for (Document doc : collection.find()) {
            rides.add(documentToRideDTO(doc));
        }
        return rides;
    }

    @Override
    public void saveRide(RideDTO ride) {
        Document doc = rideToDocument(ride);
        try {
            collection.insertOne(doc);
        } catch (MongoWriteException e) {
            System.out.println("Trying to put a duplicate ride in the database");
        }
    }

    @Override
    public void updateRide(RideDTO ride) {
        Document doc = rideToDocument(ride);
        collection.replaceOne(eq("id", ride.getId()), doc);
    }

    @Override
    public void cleanDatabase() {
        collection.drop();
    }

    private RideDTO documentToRideDTO(Document doc) {
        return new RideDTO(
                doc.getString("id"),
                new UserDTO(doc.getString("userId"), doc.getInteger("userCredit"), doc.getString("userPermission")),
                new EBikeDTO(doc.getString("ebikeId"), doc.getString("ebikeState"), doc.getDouble("ebikeX"), doc.getDouble("ebikeY"), doc.getDouble("ebikeDirectionX"), doc.getDouble("ebikeDirectionY"), doc.getDouble("ebikeSpeed"), doc.getInteger("ebikeBatteryLevel")),
                doc.getDate("startedDate"),
                Optional.ofNullable(doc.getDate("endDate"))
        );
    }

    private Document rideToDocument(RideDTO ride) {
        return new Document("id", ride.getId())
                .append("userId", ride.getUser().getId())
                .append("userCredit", ride.getUser().getCredit())
                .append("userPermission", ride.getUser().getPermission())
                .append("ebikeId", ride.getEbike().getId())
                .append("ebikeState", ride.getEbike().getState())
                .append("ebikeX", ride.getEbike().getX())
                .append("ebikeY", ride.getEbike().getY())
                .append("ebikeDirectionX", ride.getEbike().getDirectionX())
                .append("ebikeDirectionY", ride.getEbike().getDirectionY())
                .append("ebikeSpeed", ride.getEbike().getSpeed())
                .append("ebikeBatteryLevel", ride.getEbike().getBatteryLevel())
                .append("startedDate", ride.getStartedDate())
                .append("endDate", ride.getEndDate().orElse(null));
    }
}