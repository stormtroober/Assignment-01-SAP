// MySqlEBikeDatabase.java
package sap.ass01.layered.database.mysql;

import sap.ass01.layered.database.implementation.MySqlDatabaseImpl;
import sap.ass01.layered.database.dto.EBikeDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlEBikeDatabase extends MySqlDatabaseImpl<EBikeDTO> {

    public MySqlEBikeDatabase() {
        super(
            "jdbc:mysql://localhost:3306/ebikeDB",
            "ebikeUser",
            "password",
            "ebikes",
            EBikeDTO::id,
            MySqlEBikeDatabase::resultSetToEBikeDTO,
            EBikeDTO.class
        );
    }

    private static EBikeDTO resultSetToEBikeDTO(ResultSet rs) {
        try {
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
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping ResultSet to EBikeDTO", e);
        }
    }
}