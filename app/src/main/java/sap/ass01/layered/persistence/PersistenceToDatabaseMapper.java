package sap.ass01.layered.persistence;


import sap.ass01.layered.database.dto.EBikeDTO;
import sap.ass01.layered.database.dto.RideDTO;
import sap.ass01.layered.database.dto.UserDTO;

public class PersistenceToDatabaseMapper {

    public static UserDTO toDatabaseDTO(sap.ass01.layered.persistence.dto.UserDTO userDTO) {
        return new UserDTO(userDTO.id(), userDTO.credit(), userDTO.permission());
    }

    public static EBikeDTO toDatabaseDTO(sap.ass01.layered.persistence.dto.EBikeDTO ebikeDTO) {
        return new EBikeDTO(
                ebikeDTO.id(),
                ebikeDTO.state(),
                ebikeDTO.x(),
                ebikeDTO.y(),
                ebikeDTO.directionX(),
                ebikeDTO.directionY(),
                ebikeDTO.speed(),
                ebikeDTO.batteryLevel()
        );
    }

    public static RideDTO toDatabaseDTO(sap.ass01.layered.persistence.dto.RideDTO rideDTO) {
        return new RideDTO(
                rideDTO.id(),
                toDatabaseDTO(rideDTO.user()),
                toDatabaseDTO(rideDTO.bike()),
                rideDTO.startedDate(),
                rideDTO.endDate()
        );
    }
}