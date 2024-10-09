package sap.ass01.layered.persistence.user;

import sap.ass01.layered.persistence.dto.UserDTO;
import sap.ass01.layered.persistence.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLUserRepository implements UserRepository {

    private final Connection connection;

    public MySQLUserRepository() {
        try {
            // Establish a connection to the MySQL database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebikeDB", "ebikeUser", "password");
            // Create the users table if it does not exist
            createTableIfNotExists();
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    private void createTableIfNotExists() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                "id VARCHAR(255) PRIMARY KEY," +
                "credit INT," +
                "permission VARCHAR(50)" +
                ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        }
    }

    @Override
    public Optional<UserDTO> findUserById(String userId) {
        String selectSQL = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(resultSetToUserDTO(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<UserDTO> users = new ArrayList<>();
        String selectSQL = "SELECT * FROM users";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            while (rs.next()) {
                users.add(resultSetToUserDTO(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void saveUser(UserDTO user) {
        String insertSQL = "INSERT INTO users (id, credit, permission) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, user.id());
            pstmt.setInt(2, user.credit());
            pstmt.setString(3, user.permission());
            pstmt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Trying to put a duplicate user in the database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(UserDTO user) {
        String updateSQL = "UPDATE users SET credit = ?, permission = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setInt(1, user.credit());
            pstmt.setString(2, user.permission());
            pstmt.setString(3, user.id());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cleanDatabase() {
        String deleteSQL = "DELETE FROM users";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(deleteSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private UserDTO resultSetToUserDTO(ResultSet rs) throws SQLException {
        return new UserDTO(
                rs.getString("id"),
                rs.getInt("credit"),
                rs.getString("permission")
        );
    }
}