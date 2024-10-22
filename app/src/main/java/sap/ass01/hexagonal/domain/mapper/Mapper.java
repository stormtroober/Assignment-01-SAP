package sap.ass01.hexagonal.domain.mapper;


import sap.ass01.hexagonal.application.entities.EBikeDTO;
import sap.ass01.hexagonal.application.entities.RideDTO;
import sap.ass01.hexagonal.application.entities.UserDTO;
import sap.ass01.hexagonal.domain.model.EBike;
import sap.ass01.hexagonal.domain.model.Ride;
import sap.ass01.hexagonal.domain.model.User;

public class Mapper {
    public static EBikeDTO toDTO(final EBike ebike){
        return new EBikeDTO(ebike.getId(), ebike.getLocation().x(), ebike.getLocation().y(), ebike.getState().toString(), ebike.getBatteryLevel());
    }

    public static UserDTO toDTO(final User user){
        return new UserDTO(user.getId(), user.getCredit(), user.getType().equals(User.UserType.ADMIN));
    }

    public static RideDTO toDTO(final Ride ride){
        return new RideDTO(ride.getId(), toDTO(ride.getEbike()), toDTO(ride.getUser()));
    }

    public static Ride toModel(final RideDTO rideDTO){
        return new Ride(rideDTO.id(), toModel(rideDTO.user()), toModel(rideDTO.ebike()));
    }

    public static User toModel(final UserDTO userDTO){
        return new User(userDTO.id(), userDTO.admin() ? User.UserType.ADMIN : User.UserType.USER, userDTO.credit());
    }

    public static EBike toModel(final EBikeDTO ebikeDTO){
        return new EBike(ebikeDTO.id(), ebikeDTO.x(), ebikeDTO.y(), EBike.EBikeState.valueOf(ebikeDTO.state()), ebikeDTO.battery());
    }
}
