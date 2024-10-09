package sap.ass01.layered.persistence.mysql;

import sap.ass01.layered.persistence.DTO.EBikeDTO;
import sap.ass01.layered.persistence.repository.EBikeRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySqlEBikeRepository implements EBikeRepository {

    private final Connection connection;

    public MySqlEBikeRepository() {
        try {
            // Establish a connection to the MySQL database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebikeDB", "ebikeUser", "password");
            // Create the ebikes table if it does not exist
            createTableIfNotExists();
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    private void createTableIfNotExists() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS ebikes (" +
                "id VARCHAR(255) PRIMARY KEY," +
                "state VARCHAR(50)," +
                "x DOUBLE," +
                "y DOUBLE," +
                "directionX DOUBLE," +
                "directionY DOUBLE," +
                "speed DOUBLE," +
                "batteryLevel INT" +
                ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        }
    }

    public void cleanDatabase() {
        String deleteSQL = "DELETE FROM ebikes";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(deleteSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<EBikeDTO> findEBikeById(String ebikeId) {
        String selectSQL = "SELECT * FROM ebikes WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setString(1, ebikeId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(resultSetToEBikeDTO(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<EBikeDTO> findAllEBikes() {
        List<EBikeDTO> ebikes = new ArrayList<>();
        String selectSQL = "SELECT * FROM ebikes";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            while (rs.next()) {
                ebikes.add(resultSetToEBikeDTO(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ebikes;
    }

    @Override
    public void updateEBike(EBikeDTO ebike) {
        String updateSQL = "UPDATE ebikes SET state = ?, x = ?, y = ?, directionX = ?, directionY = ?, speed = ?, batteryLevel = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setString(1, ebike.getState());
            pstmt.setDouble(2, ebike.getX());
            pstmt.setDouble(3, ebike.getY());
            pstmt.setDouble(4, ebike.getDirectionX());
            pstmt.setDouble(5, ebike.getDirectionY());
            pstmt.setDouble(6, ebike.getSpeed());
            pstmt.setInt(7, ebike.getBatteryLevel());
            pstmt.setString(8, ebike.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveEBike(EBikeDTO ebike) {
        String insertSQL = "INSERT INTO ebikes (id, state, x, y, directionX, directionY, speed, batteryLevel) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, ebike.getId());
            pstmt.setString(2, ebike.getState());
            pstmt.setDouble(3, ebike.getX());
            pstmt.setDouble(4, ebike.getY());
            pstmt.setDouble(5, ebike.getDirectionX());
            pstmt.setDouble(6, ebike.getDirectionY());
            pstmt.setDouble(7, ebike.getSpeed());
            pstmt.setInt(8, ebike.getBatteryLevel());
            pstmt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Trying to put a duplicate ebike in the database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private EBikeDTO resultSetToEBikeDTO(ResultSet rs) throws SQLException {
        return new EBikeDTO(
                rs.getString("id"),
                rs.getString("state"),
                rs.getDouble("x"),
                rs.getDouble("y"),
                rs.getDouble("directionX"),
                rs.getDouble("directionY"),
                rs.getDouble("speed"),
                rs.getInt("batteryLevel")
        );
    }
}
