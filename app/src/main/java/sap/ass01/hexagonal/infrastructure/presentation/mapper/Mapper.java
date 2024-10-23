package sap.ass01.hexagonal.infrastructure.presentation.mapper;

import sap.ass01.hexagonal.application.entities.EBikeState;
import sap.ass01.hexagonal.infrastructure.presentation.models.EBikeViewModel;
import sap.ass01.hexagonal.infrastructure.presentation.models.RideViewModel;
import sap.ass01.hexagonal.infrastructure.presentation.models.UserViewModel;
import sap.ass01.hexagonal.infrastructure.presentation.plugin.EBikeDTOExt;
import sap.ass01.hexagonal.application.entities.EBikeDTO;
import sap.ass01.hexagonal.application.entities.UserDTO;
import sap.ass01.hexagonal.application.entities.RideDTO;

public class Mapper {

    // Mapping UserDTO to UserViewModel
    public static UserViewModel toDomain(UserDTO userDTO) {
        return new UserViewModel(userDTO.id(), userDTO.credit(), userDTO.admin());
    }

    // Mapping EBikeDTO to EBikeViewModel
    public static EBikeViewModel toDomain(EBikeDTO eBikeDTO) {
        EBikeViewModel.EBikeState state = EBikeViewModel.EBikeState.valueOf(eBikeDTO.state().toString());
        return new EBikeViewModel(eBikeDTO.id(), eBikeDTO.x(), eBikeDTO.y(), eBikeDTO.battery(), state);
    }

    // Mapping RideDTO to RideViewModel
    public static RideViewModel toDomain(RideDTO rideDTO, RideViewModel rideViewModel) {
        return new RideViewModel(
                rideDTO.id(),
                rideViewModel.user().updateCredit(rideDTO.user().credit()),
                rideViewModel.bike().updateBatteryLevel(rideDTO.ebike().battery()).updateLocation(rideDTO.ebike().x(), rideDTO.ebike().y()

        ));
    }

    public static EBikeViewModel toDomain(EBikeDTOExt eBikeDTOExt) {
        EBikeViewModel.EBikeState state = EBikeViewModel.EBikeState.valueOf(eBikeDTOExt.state().toUpperCase());
        return new EBikeViewModel(eBikeDTOExt.id(), eBikeDTOExt.x(), eBikeDTOExt.y(), eBikeDTOExt.batteryLevel(), state, eBikeDTOExt.color());
    }

    // Optionally, reverse mapping for scenarios where it's needed
    public static UserDTO toDTO(UserViewModel userViewModel) {
        return new UserDTO(
                userViewModel.id(), userViewModel.credit(), userViewModel.admin());
    }

    public static EBikeDTO toDTO(EBikeViewModel ebikeViewModel) {
        String state = ebikeViewModel.state().name().toLowerCase();
        return new EBikeDTO(
                ebikeViewModel.id(),
                ebikeViewModel.x(),
                ebikeViewModel.y(),
                EBikeState.valueOf(state),
                ebikeViewModel.batteryLevel()

        );
    }

    public static RideViewModel toDTO(RideViewModel rideViewModel) {
        return new RideViewModel(
                rideViewModel.id(),
                rideViewModel.user(),
                rideViewModel.bike()
        );
    }
}
