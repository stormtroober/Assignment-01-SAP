package sap.ass01.layered.database;

import java.util.List;
import java.util.Optional;

/**
 * Interface representing a generic database.
 *
 * @param <T> the type of the entity to be stored in the database
 */
public interface Database<T> {

    /**
     * Saves the given entity to the database.
     *
     * @param entity the entity to be saved
     */
    void save(T entity);

    /**
     * Finds an entity by its ID.
     *
     * @param id the ID of the entity to be found
     * @return an Optional containing the found entity, or empty if not found
     */
    Optional<T> findById(String id);

    /**
     * Retrieves all entities from the database.
     *
     * @return a list of all entities
     */
    List<T> findAll();

    /**
     * Updates the given entity in the database.
     *
     * @param entity the entity to be updated
     */
    void update(T entity);

    /**
     * Cleans the database, removing all entities.
     */
    void clean();
}