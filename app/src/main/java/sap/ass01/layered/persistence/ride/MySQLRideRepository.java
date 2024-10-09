package sap.ass01.layered.persistence.ride;

import sap.ass01.layered.persistence.dto.RideDTO;
import sap.ass01.layered.persistence.dto.UserDTO;
import sap.ass01.layered.persistence.dto.EBikeDTO;
import sap.ass01.layered.persistence.repository.RideRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLRideRepository implements RideRepository {

    private final Connection connection;

    public MySQLRideRepository() {
        try {
            // Establish a connection to the MySQL database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebikeDB", "ebikeUser", "password");
            // Create the rides table if it does not exist
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
                "userPermission VARCHAR(50)," +
                "ebikeId VARCHAR(255)," +
                "ebikeState VARCHAR(50)," +
                "ebikeX DOUBLE," +
                "ebikeY DOUBLE," +
                "ebikeDirectionX DOUBLE," +
                "ebikeDirectionY DOUBLE," +
                "ebikeSpeed DOUBLE," +
                "ebikeBatteryLevel INT," +
                "startedDate TIMESTAMP," +
                "endDate TIMESTAMP" +
                ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        }
    }

    @Override
    public Optional<RideDTO> findRideById(String rideId) {
        String selectSQL = "SELECT * FROM rides WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setString(1, rideId);
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
    public List<RideDTO> findAllRides() {
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
    public void saveRide(RideDTO ride) {
        String insertSQL = "INSERT INTO rides (id, userId, userCredit, userPermission, ebikeId, ebikeState, ebikeX, ebikeY, ebikeDirectionX, ebikeDirectionY, ebikeSpeed, ebikeBatteryLevel, startedDate, endDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, ride.getId());
            pstmt.setString(2, ride.getUser().getId());
            pstmt.setInt(3, ride.getUser().getCredit());
            pstmt.setString(4, ride.getUser().getPermission());
            pstmt.setString(5, ride.getEbike().getId());
            pstmt.setString(6, ride.getEbike().getState());
            pstmt.setDouble(7, ride.getEbike().getX());
            pstmt.setDouble(8, ride.getEbike().getY());
            pstmt.setDouble(9, ride.getEbike().getDirectionX());
            pstmt.setDouble(10, ride.getEbike().getDirectionY());
            pstmt.setDouble(11, ride.getEbike().getSpeed());
            pstmt.setInt(12, ride.getEbike().getBatteryLevel());
            pstmt.setTimestamp(13, new Timestamp(ride.getStartedDate().getTime()));
            pstmt.setTimestamp(14, ride.getEndDate().map(date -> new Timestamp(date.getTime())).orElse(null));
            pstmt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Trying to put a duplicate ride in the database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateRide(RideDTO ride) {
        String updateSQL = "UPDATE rides SET userId = ?, userCredit = ?, userPermission = ?, ebikeId = ?, ebikeState = ?, ebikeX = ?, ebikeY = ?, ebikeDirectionX = ?, ebikeDirectionY = ?, ebikeSpeed = ?, ebikeBatteryLevel = ?, startedDate = ?, endDate = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setString(1, ride.getUser().getId());
            pstmt.setInt(2, ride.getUser().getCredit());
            pstmt.setString(3, ride.getUser().getPermission());
            pstmt.setString(4, ride.getEbike().getId());
            pstmt.setString(5, ride.getEbike().getState());
            pstmt.setDouble(6, ride.getEbike().getX());
            pstmt.setDouble(7, ride.getEbike().getY());
            pstmt.setDouble(8, ride.getEbike().getDirectionX());
            pstmt.setDouble(9, ride.getEbike().getDirectionY());
            pstmt.setDouble(10, ride.getEbike().getSpeed());
            pstmt.setInt(11, ride.getEbike().getBatteryLevel());
            pstmt.setTimestamp(12, new Timestamp(ride.getStartedDate().getTime()));
            pstmt.setTimestamp(13, ride.getEndDate().map(date -> new Timestamp(date.getTime())).orElse(null));
            pstmt.setString(14, ride.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cleanDatabase() {
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
                new UserDTO(rs.getString("userId"), rs.getInt("userCredit"), rs.getString("userPermission")),
                new EBikeDTO(rs.getString("ebikeId"), rs.getString("ebikeState"), rs.getDouble("ebikeX"), rs.getDouble("ebikeY"), rs.getDouble("ebikeDirectionX"), rs.getDouble("ebikeDirectionY"), rs.getDouble("ebikeSpeed"), rs.getInt("ebikeBatteryLevel")),
                rs.getTimestamp("startedDate"),
                Optional.ofNullable(rs.getTimestamp("endDate"))
        );
    }
}