package sap.ass01.layered.database.mysql;

import sap.ass01.layered.database.implementation.MySqlDatabaseImpl;
import sap.ass01.layered.database.dto.UserDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlUserDatabase extends MySqlDatabaseImpl<UserDTO> {

    public MySqlUserDatabase() {
        super(
            "jdbc:mysql://localhost:3306/ebikeDB",
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
                rs.getString("permission")
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping ResultSet to UserDTO", e);
        }
    }
}