package sap.ass01.hexagonal.infrastructure.presentation.mapper;

import sap.ass01.hexagonal.application.ports.entities.EBikeDTO;
import sap.ass01.hexagonal.application.ports.entities.RideDTO;
import sap.ass01.hexagonal.application.ports.entities.UserDTO;
import sap.ass01.hexagonal.infrastructure.presentation.models.EBikeViewModel;
import sap.ass01.hexagonal.infrastructure.presentation.models.RideViewModel;
import sap.ass01.hexagonal.infrastructure.presentation.models.UserViewModel;
import sap.ass01.hexagonal.infrastructure.presentation.plugin.EBikeDTOExt;


public class Mapper {

    public static UserViewModel toDomain(UserDTO userDTO) {
        return new UserViewModel(userDTO.id(), userDTO.credit(), userDTO.admin());
    }

    public static EBikeViewModel toDomain(EBikeDTO eBikeDTO) {
        EBikeViewModel.EBikeState state = EBikeViewModel.EBikeState.valueOf(eBikeDTO.state().toString());
        return new EBikeViewModel(eBikeDTO.id(), eBikeDTO.x(), eBikeDTO.y(), eBikeDTO.battery(), state);
    }

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
                ebikeViewModel.state().name(),
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
