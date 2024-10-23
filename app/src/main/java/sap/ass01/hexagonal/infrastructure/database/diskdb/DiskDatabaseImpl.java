// DiskDatabaseImpl.java
package sap.ass01.hexagonal.infrastructure.database.diskdb;

import com.google.gson.Gson;
import sap.ass01.layered.database.Database;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class DiskDatabaseImpl<T> implements Database<T> {

    private final File saveFile;
    private final Function<T, String> getId;
    private final Type listType;

    public DiskDatabaseImpl(String filePath, Function<T, String> getId, Type listType) {
        this.saveFile = new File(filePath);
        this.getId = getId;
        this.listType = listType;
    }

    protected Gson createGson() {
        return new Gson();
    }

    @Override
    public void save(T entity) {
        List<T> entities = readEntitiesFromFile();
        boolean entityExists = entities.stream()
                .anyMatch(existingEntity -> getId.apply(existingEntity).equals(getId.apply(entity)));

        if (!entityExists) {
            entities.add(entity);
            writeEntitiesToFile(entities);
        }
    }

    @Override
    public Optional<T> findById(String id) {
        List<T> entities = readEntitiesFromFile();
        return entities.stream()
                .filter(entity -> getId.apply(entity).equals(id))
                .findFirst();
    }

    @Override
    public List<T> findAll() {
        return readEntitiesFromFile();
    }

    @Override
    public void update(T entity) {
        List<T> entities = readEntitiesFromFile();
        for (int i = 0; i < entities.size(); i++) {
            if (getId.apply(entities.get(i)).equals(getId.apply(entity))) {
                entities.set(i, entity);
                writeEntitiesToFile(entities);
                return;
            }
        }
    }

    @Override
    public void clean() {
        if (saveFile.exists()) {
            saveFile.delete();
        }
    }

    private List<T> readEntitiesFromFile() {
        if (!saveFile.exists()) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(saveFile)) {
            return createGson().fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void writeEntitiesToFile(List<T> entities) {
        try {
            File parentDir = saveFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            try (FileWriter writer = new FileWriter(saveFile)) {
                createGson().toJson(entities, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}