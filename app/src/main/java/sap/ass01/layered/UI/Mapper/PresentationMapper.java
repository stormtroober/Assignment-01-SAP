package sap.ass01.layered.UI.Mapper;

import sap.ass01.layered.services.dto.UserDTO;
import sap.ass01.layered.services.dto.EBikeDTO;
import sap.ass01.layered.services.dto.RideDTO;
import sap.ass01.layered.UI.Models.EBikeViewModel;
import sap.ass01.layered.UI.Models.UserViewModel;
import sap.ass01.layered.UI.Models.RideViewModel;

public class PresentationMapper {

    // Mapping UserDTO to UserViewModel
    public static UserViewModel toUserViewModel(UserDTO userDTO) {
        return new UserViewModel(userDTO);
    }

    // Mapping EBikeDTO to EBikeViewModel
    public static EBikeViewModel toEBikeViewModel(EBikeDTO eBikeDTO) {
        return new EBikeViewModel(eBikeDTO);
    }

    // Mapping RideDTO to RideViewModel
    public static RideViewModel toRideViewModel(RideDTO rideDTO) {
        return new RideViewModel(rideDTO);
    }

    // Optionally, reverse mapping for scenarios where it's needed
    public static UserDTO toUserDTO(UserViewModel userViewModel) {
        return new UserDTO(userViewModel.getId(), userViewModel.getCredit(), userViewModel.isAdmin());
    }

    public static EBikeDTO toEBikeDTO(EBikeViewModel ebikeViewModel) {
        return new EBikeDTO(
                ebikeViewModel.getId(),
                ebikeViewModel.getX(),
                ebikeViewModel.getY(),
                ebikeViewModel.getBatteryLevel(),
                ebikeViewModel.getState()
        );
    }

    public static RideDTO toRideDTO(RideViewModel rideViewModel) {
        return new RideDTO(
                rideViewModel.getId(),
                rideViewModel.getX(),
                rideViewModel.getY(),
                rideViewModel.getCredit(),
                rideViewModel.getCharge()
        );
    }
}
