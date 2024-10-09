package sap.ass01.layered.domain.mapper;

import sap.ass01.layered.persistence.dto.EBikeDTO;
import sap.ass01.layered.persistence.dto.RideDTO;
import sap.ass01.layered.persistence.dto.UserDTO;
import sap.ass01.layered.domain.model.*;

public class Mapper {

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getId(), user.getCredit(), user.getPermission().name());
    }

    public static User toUser(UserDTO userDTO) {
        return new User(userDTO.getId(), User.UserType.valueOf(userDTO.getPermission()));
    }

    public static EBikeDTO toEBikeDTO(EBike ebike) {
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

    public static EBike toEBike(EBikeDTO ebikeDTO) {
        EBike ebike = new EBike(ebikeDTO.getId());
        ebike.updateState(EBike.EBikeState.valueOf(ebikeDTO.getState()));
        ebike.updateLocation(new P2d(ebikeDTO.getX(), ebikeDTO.getY()));
        ebike.updateDirection(new V2d(ebikeDTO.getDirectionX(), ebikeDTO.getDirectionY()));
        ebike.updateSpeed(ebikeDTO.getSpeed());
        return ebike;
    }

    public static RideDTO toRideDTO(Ride ride) {
        return new RideDTO(
                ride.getId(),
                toUserDTO(ride.getUser()),
                toEBikeDTO(ride.getEBike()),
                ride.getStartedDate(),
                ride.getEndDate()
        );
    }

    public static Ride toRide(RideDTO rideDTO, User user, EBike ebike) {
        return new Ride(rideDTO.getId(), user, ebike);
    }
}