package sap.ass01.hexagonal.infrastructure.database.mysql;

import sap.ass01.hexagonal.application.ports.entities.EBikeDTO;
import sap.ass01.hexagonal.infrastructure.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlEBikeDatabase extends MySqlDatabaseImpl<EBikeDTO> implements Database<EBikeDTO> {

    public MySqlEBikeDatabase() {
        super(
            "jdbc:mysql://localhost:3307/ebikeDB-H",
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
            rs.getDouble("x"),
            rs.getDouble("y"),
            rs.getString("state"),
            rs.getInt("battery")
        );
    } catch (SQLException e) {
        throw new RuntimeException("Error mapping ResultSet to EBikeDTO", e);
    }
}
}