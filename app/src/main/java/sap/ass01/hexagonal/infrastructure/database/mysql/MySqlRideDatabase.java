package sap.ass01.hexagonal.infrastructure.database.mysql;

import sap.ass01.hexagonal.application.ports.entities.EBikeDTO;
import sap.ass01.hexagonal.application.ports.entities.RideDTO;
import sap.ass01.hexagonal.application.ports.entities.UserDTO;
import sap.ass01.hexagonal.infrastructure.database.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySqlRideDatabase implements Database<RideDTO> {

    private final Connection connection;

    public MySqlRideDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/ebikeDB-H", "ebikeUser", "password");
            createTableIfNotExists();
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    private void createTableIfNotExists() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS rides (" +
                "id VARCHAR(255) PRIMARY KEY," +
                "userId VARCHAR(255)," +
                "userCredit INT," +
                "userAdmin BOOLEAN," +
                "ebikeId VARCHAR(255)," +
                "ebikeState VARCHAR(50)," +
                "ebikeX DOUBLE," +
                "ebikeY DOUBLE," +
                "ebikeBatteryLevel INT," +
                "startedDate TIMESTAMP," +
                "endDate TIMESTAMP" +
                ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        }
    }

    @Override
public void save(RideDTO ride) {
    String insertSQL = "INSERT INTO rides (id, userId, userCredit, userAdmin, ebikeId, ebikeState, ebikeX, ebikeY, ebikeBatteryLevel, startedDate, endDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
        pstmt.setString(1, ride.id());
        pstmt.setString(2, ride.user().id());
        pstmt.setInt(3, ride.user().credit());
        pstmt.setBoolean(4, ride.user().admin());
        pstmt.setString(5, ride.ebike().id());
        pstmt.setString(6, ride.ebike().state());
        pstmt.setDouble(7, ride.ebike().x());
        pstmt.setDouble(8, ride.ebike().y());
        pstmt.setInt(9, ride.ebike().battery());
        if (ride.startedDate() != null) {
            pstmt.setTimestamp(10, new Timestamp(ride.startedDate().getTime()));
        } else {
            pstmt.setTimestamp(10, null);
        }
        pstmt.setTimestamp(11, ride.endDate().map(date -> new Timestamp(date.getTime())).orElse(null));
        pstmt.executeUpdate();
    } catch (SQLIntegrityConstraintViolationException e) {
        System.out.println("Trying to put a duplicate ride in the database");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    @Override
    public Optional<RideDTO> findById(String id) {
        String selectSQL = "SELECT * FROM rides WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(resultSetToRideDTO(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<RideDTO> findAll() {
        List<RideDTO> rides = new ArrayList<>();
        String selectSQL = "SELECT * FROM rides";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            while (rs.next()) {
                rides.add(resultSetToRideDTO(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rides;
    }

    @Override
    public void update(RideDTO ride) {
        String updateSQL = "UPDATE rides SET userId = ?, userCredit = ?, userAdmin = ?, ebikeId = ?, ebikeState = ?, ebikeX = ?, ebikeY = ?, ebikeBatteryLevel = ?, startedDate = ?, endDate = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setString(1, ride.user().id());
            pstmt.setInt(2, ride.user().credit());
            pstmt.setBoolean(3, ride.user().admin());
            pstmt.setString(4, ride.ebike().id());
            pstmt.setString(5, ride.ebike().state());
            pstmt.setDouble(6, ride.ebike().x());
            pstmt.setDouble(7, ride.ebike().y());
            pstmt.setInt(8, ride.ebike().battery());
            pstmt.setTimestamp(9, new Timestamp(ride.startedDate().getTime()));
            pstmt.setTimestamp(10, ride.endDate().map(date -> new Timestamp(date.getTime())).orElse(null));
            pstmt.setString(11, ride.id());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clean() {
        String deleteSQL = "DELETE FROM rides";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(deleteSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private RideDTO resultSetToRideDTO(ResultSet rs) throws SQLException {
        return new RideDTO(
                rs.getString("id"),
                new EBikeDTO(
                        rs.getString("ebikeId"),
                        rs.getDouble("ebikeX"),
                        rs.getDouble("ebikeY"),
                        rs.getString("ebikeState"),
                        rs.getInt("ebikeBatteryLevel")
                ),
                new UserDTO(
                        rs.getString("userId"),
                        rs.getInt("userCredit"),
                        rs.getBoolean("userAdmin")
                ),
                rs.getTimestamp("startedDate"),
                Optional.ofNullable(rs.getTimestamp("endDate"))
        );
    }
}