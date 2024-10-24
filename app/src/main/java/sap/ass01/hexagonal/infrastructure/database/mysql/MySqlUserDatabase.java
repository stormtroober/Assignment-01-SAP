package sap.ass01.hexagonal.infrastructure.database.mysql;

import sap.ass01.hexagonal.application.ports.entities.UserDTO;
import sap.ass01.hexagonal.infrastructure.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlUserDatabase extends MySqlDatabaseImpl<UserDTO> implements Database<UserDTO> {

    public MySqlUserDatabase() {
        super(
            "jdbc:mysql://localhost:3307/ebikeDB-H",
            "ebikeUser",
            "password",
            "users",
            UserDTO::id,
            MySqlUserDatabase::resultSetToUserDTO,
            UserDTO.class
        );
    }

    private static UserDTO resultSetToUserDTO(ResultSet rs) {
        try {
            return new UserDTO(
                rs.getString("id"),
                rs.getInt("credit"),
                rs.getBoolean("admin")
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping ResultSet to UserDTO", e);
        }
    }
}