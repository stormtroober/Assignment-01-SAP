package sap.ass01.layered.UI.Mapper;

import sap.ass01.layered.services.dto.UserDTO;
import sap.ass01.layered.services.dto.EBikeDTO;
import sap.ass01.layered.services.dto.RideDTO;
import sap.ass01.layered.UI.Models.EBikeViewModel;
import sap.ass01.layered.UI.Models.UserViewModel;
import sap.ass01.layered.UI.Models.RideViewModel;

public class Mapper {

    // Mapping UserDTO to UserViewModel
    public static UserViewModel toDomain(UserDTO userDTO) {
        return new UserViewModel(userDTO.id(), userDTO.credit(), userDTO.admin());
    }

    // Mapping EBikeDTO to EBikeViewModel
    public static EBikeViewModel toDomain(EBikeDTO eBikeDTO) {

        return new EBikeViewModel(eBikeDTO.id(), eBikeDTO.x(), eBikeDTO.y(), eBikeDTO.batteryLevel(), eBikeDTO.state());
    }

    // Mapping RideDTO to RideViewModel
    public static RideViewModel toDomain(RideDTO rideDTO, RideViewModel rideViewModel) {
        return new RideViewModel(
                rideDTO.id(),
                rideViewModel.user().updateCredit(rideDTO.credit()),
                rideViewModel.bike().updateBatteryLevel(rideDTO.charge())
        );
    }

    // Optionally, reverse mapping for scenarios where it's needed
    public static UserDTO toDTO(UserViewModel userViewModel) {
        return new UserDTO(userViewModel.id(), userViewModel.credit(), userViewModel.admin());
    }

    public static EBikeDTO toDTO(EBikeViewModel ebikeViewModel) {
        return new EBikeDTO(
                ebikeViewModel.id(),
                ebikeViewModel.x(),
                ebikeViewModel.y(),
                ebikeViewModel.batteryLevel(),
                ebikeViewModel.state()

        );
    }

    public static RideDTO toDTO(RideViewModel rideViewModel) {
        return new RideDTO(
                rideViewModel.id(),
                rideViewModel.bike().x(),
                rideViewModel.bike().y(),
                rideViewModel.user().credit(),
                rideViewModel.bike().batteryLevel()
        );
    }
}
