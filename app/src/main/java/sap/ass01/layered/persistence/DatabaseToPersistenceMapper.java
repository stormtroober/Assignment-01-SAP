package sap.ass01.layered.persistence;

import sap.ass01.layered.persistence.dto.EBikeDTO;
import sap.ass01.layered.persistence.dto.RideDTO;
import sap.ass01.layered.persistence.dto.UserDTO;

public class DatabaseToPersistenceMapper {

    public static UserDTO toPersistenceDTO(sap.ass01.layered.database.dto.UserDTO userDTO) {
        return new UserDTO(userDTO.id(), userDTO.credit(), userDTO.permission());
    }

    public static EBikeDTO toPersistenceDTO(sap.ass01.layered.database.dto.EBikeDTO ebikeDTO) {
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

    public static RideDTO toPersistenceDTO(sap.ass01.layered.database.dto.RideDTO rideDTO) {
        return new RideDTO(
                rideDTO.id(),
                toPersistenceDTO(rideDTO.user()),
                toPersistenceDTO(rideDTO.bike()),
                rideDTO.startedDate(),
                rideDTO.endDate()
        );
    }
}