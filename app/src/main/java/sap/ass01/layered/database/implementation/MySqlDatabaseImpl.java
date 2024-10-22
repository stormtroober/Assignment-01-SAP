package sap.ass01.layered.database.implementation;

import com.google.gson.Gson;
import sap.ass01.layered.database.Database;
import sap.ass01.layered.database.dto.RideDTO;
import sap.ass01.layered.database.dto.UserDTO;
import sap.ass01.layered.database.dto.EBikeDTO;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MySqlDatabaseImpl<T> implements Database<T> {

    private final Connection connection;
    private final Function<T, String> getId;
    private final String tableName;
    private final Function<ResultSet, T> resultSetToEntity;
    private final Class<T> type;

    public MySqlDatabaseImpl(String url, String user, String password, String tableName, Function<T, String> getId, Function<ResultSet, T> resultSetToEntity, Class<T> type) {
        this.tableName = tableName;
        this.getId = getId;
        this.resultSetToEntity = resultSetToEntity;
        this.type = type;
        try {
            this.connection = DriverManager.getConnection(url, user, password);
            createTableIfNotExists();
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    private void createTableIfNotExists() throws SQLException {
        StringBuilder createTableSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + " (");
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            createTableSQL.append(field.getName()).append(" ").append(getSQLType(field.getType())).append(", ");
        }
        createTableSQL.setLength(createTableSQL.length() - 2); // Remove the last comma and space
        createTableSQL.append(")");
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL.toString());
        }
    }

    private String getSQLType(Class<?> fieldType) {
        if (fieldType == String.class) {
            return "VARCHAR(255)";
        } else if (fieldType == int.class || fieldType == Integer.class) {
            return "INT";
        } else if (fieldType == double.class || fieldType == Double.class) {
            return "DOUBLE";
        } else if (fieldType == Date.class) {
            return "TIMESTAMP";
        } else if (fieldType.isAssignableFrom(Optional.class)) {
            return "TIMESTAMP";
        } else if (fieldType == UserDTO.class || fieldType == EBikeDTO.class) {
            return "JSON";
        } else {
            throw new IllegalArgumentException("Unsupported field type: " + fieldType.getName());
        }
    }

    @Override
    public void save(T entity) {
        StringBuilder insertSQL = new StringBuilder("INSERT INTO " + tableName + " (");
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            insertSQL.append(field.getName()).append(", ");
        }
        insertSQL.setLength(insertSQL.length() - 2); // Remove the last comma and space
        insertSQL.append(") VALUES (");
        for (int i = 0; i < fields.length; i++) {
            insertSQL.append("?, ");
        }
        insertSQL.setLength(insertSQL.length() - 2); // Remove the last comma and space
        insertSQL.append(")");

        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL.toString())) {
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                Object value = fields[i].get(entity);
                if (value instanceof Optional) {
                    value = ((Optional<?>) value).orElse(null);
                }
                if (value instanceof UserDTO || value instanceof EBikeDTO) {
                    pstmt.setString(i + 1, toJson(value));
                } else {
                    pstmt.setObject(i + 1, value);
                }
            }
            pstmt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Trying to put a duplicate entity in the database");
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private String toJson(Object obj) {
        // Convert the object to JSON string
        // You can use any JSON library like Jackson or Gson
        return new Gson().toJson(obj);
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
        StringBuilder updateSQL = new StringBuilder("UPDATE " + tableName + " SET ");
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            updateSQL.append(field.getName()).append(" = ?, ");
        }
        updateSQL.setLength(updateSQL.length() - 2); // Remove the last comma and space
        updateSQL.append(" WHERE id = ?");

        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL.toString())) {
            int index = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(entity);
                if (value instanceof Optional) {
                    value = ((Optional<?>) value).orElse(null);
                }
                if (value instanceof UserDTO || value instanceof EBikeDTO) {
                    pstmt.setString(index++, toJson(value));
                } else {
                    pstmt.setObject(index++, value);
                }
            }
            pstmt.setString(index, getId.apply(entity));
            pstmt.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
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