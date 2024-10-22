//package sap.ass01.layered.database.mysql;
//
//import sap.ass01.layered.database.implementation.MySqlDatabaseImpl;
//import sap.ass01.layered.database.dto.*;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Optional;
//
//public class MySqlRideDatabase extends MySqlDatabaseImpl<RideDTO> {
//
//    public MySqlRideDatabase() {
//        super(
//            "jdbc:mysql://localhost:3306/ebikeDB",
//            "ebikeUser",
//            "password",
//            "rides",
//            RideDTO::id,
//            MySqlRideDatabase::resultSetToRideDTO,
//            RideDTO.class
//        );
//    }
//
//    private static RideDTO resultSetToRideDTO(ResultSet rs) {
//    try {
//        return new RideDTO(
//            rs.getString("id"),
//            new UserDTO(rs.getString("userId"), rs.getInt("userCredit"), rs.getString("userPermission")),
//            new EBikeDTO(rs.getString("ebikeId"), rs.getString("ebikeState"), rs.getDouble("ebikeX"), rs.getDouble("ebikeY"), rs.getDouble("ebikeDirectionX"), rs.getDouble("ebikeDirectionY"), rs.getDouble("ebikeSpeed"), rs.getInt("ebikeBatteryLevel")),
//            new java.util.Date(rs.getTimestamp("startedDate").getTime()),
//            Optional.ofNullable(rs.getTimestamp("endDate")).map(Timestamp::getTime).map(java.util.Date::new)
//        );
//    } catch (SQLException e) {
//        throw new RuntimeException("Error mapping ResultSet to RideDTO", e);
//    }
//}
//}