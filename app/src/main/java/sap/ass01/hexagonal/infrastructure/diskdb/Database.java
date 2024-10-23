package sap.ass01.hexagonal.infrastructure.diskdb;

import java.util.List;
import java.util.Optional;

public interface Database<T> {
    void save(T entity);
    Optional<T> findById(String id);
    List<T> findAll();
    void update(T entity);
    void clean();
}