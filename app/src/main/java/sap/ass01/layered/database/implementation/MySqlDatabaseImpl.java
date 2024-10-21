package sap.ass01.layered.database.implementation;

import sap.ass01.layered.database.Database;
import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MySqlDatabaseImpl<T> implements Database<T> {

    private final Connection connection;
    private final Function<T, String> getId;
    private final String tableName;
    private final Function<ResultSet, T> resultSetToEntity;

    public MySqlDatabaseImpl(String url, String user, String password, String tableName, Function<T, String> getId, Function<ResultSet, T> resultSetToEntity) {
        this.tableName = tableName;
        this.getId = getId;
        this.resultSetToEntity = resultSetToEntity;
        try {
            this.connection = DriverManager.getConnection(url, user, password);
            createTableIfNotExists();
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    private void createTableIfNotExists() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "id VARCHAR(255) PRIMARY KEY," +
                "data JSON" +
                ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        }
    }

    @Override
    public void save(T entity) {
        String insertSQL = "INSERT INTO " + tableName + " (id, data) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, getId.apply(entity));
            pstmt.setString(2, new Gson().toJson(entity));
            pstmt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Trying to put a duplicate entity in the database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<T> findById(String id) {
        String selectSQL = "SELECT * FROM " + tableName + " WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(resultSetToEntity.apply(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<T> findAll() {
        List<T> entities = new ArrayList<>();
        String selectSQL = "SELECT * FROM " + tableName;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            while (rs.next()) {
                entities.add(resultSetToEntity.apply(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public void update(T entity) {
        String updateSQL = "UPDATE " + tableName + " SET data = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setString(1, new Gson().toJson(entity));
            pstmt.setString(2, getId.apply(entity));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clean() {
        String deleteSQL = "DELETE FROM " + tableName;
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(deleteSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}