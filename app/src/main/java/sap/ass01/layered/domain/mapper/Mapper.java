package sap.ass01.layered.domain.mapper;

import sap.ass01.layered.persistence.dto.EBikeDTO;
import sap.ass01.layered.persistence.dto.RideDTO;
import sap.ass01.layered.persistence.dto.UserDTO;
import sap.ass01.layered.domain.model.*;

public class Mapper {

    public static UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getCredit(), user.getType().name());
    }

    public static EBikeDTO toDTO(EBike ebike) {
        return new EBikeDTO(
                ebike.getId(),
                ebike.getState().name(),
                ebike.getLocation().x(),
                ebike.getLocation().y(),
                ebike.getDirection().x(),
                ebike.getDirection().y(),
                ebike.getSpeed(),
                ebike.getBatteryLevel()
        );
    }

    public static RideDTO toDTO(Ride ride) {
        return new RideDTO(
                ride.getId(),
                toDTO(ride.getUser()),
                toDTO(ride.getEbike()),
                ride.getStartTime(),
                ride.getEndTime()
        );
    }
    
    public static User toDomain(UserDTO userDTO) {
        return new User(userDTO.id(), User.UserType.valueOf(userDTO.permission()), userDTO.credit() );
    }

    public static EBike toDomain(EBikeDTO ebikeDTO) {
        EBike ebike = new EBike(ebikeDTO.id(), ebikeDTO.x(), ebikeDTO.y(), EBike.EBikeState.valueOf(ebikeDTO.state()), ebikeDTO.batteryLevel());
        ebike.setState(EBike.EBikeState.valueOf(ebikeDTO.state()));
        ebike.setLocation(new P2d(ebikeDTO.x(), ebikeDTO.y()));
        ebike.setDirection(new V2d(ebikeDTO.directionX(), ebikeDTO.directionY()));
        ebike.setSpeed(ebikeDTO.speed());
        return ebike;
    }

    public static Ride toDomain(RideDTO rideDTO, User user, EBike ebike) {
        return new Ride(rideDTO.id(), user, ebike);
    }
}