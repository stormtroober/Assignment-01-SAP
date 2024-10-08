package sap.ass01.layered.persistence.MongoDB;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;
import sap.ass01.layered.persistence.DTO.EBikeDTO;
import sap.ass01.layered.persistence.repository.EBikeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class MongoEBikeRepository implements EBikeRepository {

    private final MongoCollection<Document> collection;

    public MongoEBikeRepository() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("ebikeDB");
        collection = database.getCollection("ebikes");
        collection.createIndex(new Document("id", 1), new IndexOptions().unique(true));
    }

    public void cleanDatabase() {
        collection.drop();
    }

    @Override
    public Optional<EBikeDTO> findEBikeById(String ebikeId) {
        Document doc = collection.find(eq("id", ebikeId)).first();
        return Optional.ofNullable(doc).map(this::documentToEBikeDTO);
    }

    @Override
    public List<EBikeDTO> findAllEBikes() {
        List<EBikeDTO> ebikes = new ArrayList<>();
        for (Document doc : collection.find()) {
            ebikes.add(documentToEBikeDTO(doc));
        }
        return ebikes;
    }

    @Override
    public void updateEBike(EBikeDTO ebike) {
        Document doc = ebikeToDocument(ebike);
        collection.replaceOne(eq("id", ebike.getId()), doc);
    }

    @Override
    public void saveEBike(EBikeDTO ebike) {
        Document doc = ebikeToDocument(ebike);
        try{
            collection.insertOne(doc);
        } catch (MongoWriteException e){
            System.out.println("Trying to put a duplicate ebike in the database");
        }
    }

    private EBikeDTO documentToEBikeDTO(Document doc) {
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

    private Document ebikeToDocument(EBikeDTO ebike) {
        return new Document("id", ebike.getId())
                .append("state", ebike.getState())
                .append("x", ebike.getX())
                .append("y", ebike.getY())
                .append("directionX", ebike.getDirectionX())
                .append("directionY", ebike.getDirectionY())
                .append("speed", ebike.getSpeed())
                .append("batteryLevel", ebike.getBatteryLevel());
    }
}