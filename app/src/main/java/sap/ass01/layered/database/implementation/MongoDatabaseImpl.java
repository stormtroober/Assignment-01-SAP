package sap.ass01.layered.database.implementation;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;
import sap.ass01.layered.database.Database;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.mongodb.client.model.Filters.eq;

public class MongoDatabaseImpl<T> implements Database<T> {

    private final MongoCollection<Document> collection;
    private final Function<T, Document> toDocument;
    private final Function<Document, T> fromDocument;

    public MongoDatabaseImpl(String databaseName, String collectionName, Function<T, Document> toDocument, Function<Document, T> fromDocument) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        this.collection = database.getCollection(collectionName);
        this.collection.createIndex(new Document("id", 1), new IndexOptions().unique(true));
        this.toDocument = toDocument;
        this.fromDocument = fromDocument;
    }

    @Override
    public void save(T entity) {
        Document doc = toDocument.apply(entity);
        try {
            collection.insertOne(doc);
        } catch (MongoWriteException e) {
            System.out.println("Trying to put a duplicate entity in the database");
        }
    }

    @Override
    public Optional<T> findById(String id) {
        Document doc = collection.find(eq("id", id)).first();
        return Optional.ofNullable(doc).map(fromDocument);
    }

    @Override
    public List<T> findAll() {
        List<T> entities = new ArrayList<>();
        for (Document doc : collection.find()) {
            entities.add(fromDocument.apply(doc));
        }
        return entities;
    }

    @Override
    public void update(T entity) {
        Document doc = toDocument.apply(entity);
        collection.replaceOne(eq("id", doc.getString("id")), doc);
    }

    @Override
    public void clean() {
        collection.drop();
    }
}