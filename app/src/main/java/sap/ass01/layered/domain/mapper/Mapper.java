package sap.ass01.layered.domain.mapper;

import sap.ass01.layered.persistence.dto.EBikeDTO;
import sap.ass01.layered.persistence.dto.RideDTO;
import sap.ass01.layered.persistence.dto.UserDTO;
import sap.ass01.layered.domain.model.*;

public class Mapper {

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getId(), user.getCredit(), user.getType().name());
    }

    public static User toUser(UserDTO userDTO) {
        return new User(userDTO.id(), User.UserType.valueOf(userDTO.permission()));
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
        EBike ebike = new EBike(ebikeDTO.id(), ebikeDTO.x(), ebikeDTO.y());
        ebike.setState(EBike.EBikeState.valueOf(ebikeDTO.state()));
        ebike.setLocation(new P2d(ebikeDTO.x(), ebikeDTO.y()));
        ebike.setDirection(new V2d(ebikeDTO.directionX(), ebikeDTO.directionY()));
        ebike.setSpeed(ebikeDTO.speed());
        return ebike;
    }

    public static RideDTO toRideDTO(Ride ride) {
        return new RideDTO(
                ride.getId(),
                toUserDTO(ride.getUser()),
                toEBikeDTO(ride.getEbike()),
                ride.getStartTime(),
                ride.getEndTime()
        );
    }

    public static Ride toRide(RideDTO rideDTO, User user, EBike ebike) {
        return new Ride(rideDTO.id(), user, ebike);
    }
}