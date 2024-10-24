package sap.ass01.layered.presentation.mapper;

import sap.ass01.layered.services.dto.EBikeDTO;
import sap.ass01.layered.services.dto.RideDTO;
import sap.ass01.layered.services.dto.UserDTO;
import sap.ass01.layered.presentation.models.EBikeViewModel;
import sap.ass01.layered.presentation.models.RideViewModel;
import sap.ass01.layered.presentation.models.UserViewModel;
import sap.ass01.layered.presentation.plugin.EBikeDTOExt;

public class Mapper {

    public static UserViewModel toDomain(UserDTO userDTO) {
        return new UserViewModel(userDTO.id(), userDTO.credit(), userDTO.admin());
    }

    public static EBikeViewModel toDomain(EBikeDTO eBikeDTO) {
        EBikeViewModel.EBikeState state = EBikeViewModel.EBikeState.valueOf(eBikeDTO.state());
        return new EBikeViewModel(eBikeDTO.id(), eBikeDTO.x(), eBikeDTO.y(), eBikeDTO.batteryLevel(), state);
    }

    public static RideViewModel toDomain(RideDTO rideDTO, RideViewModel rideViewModel) {
        return new RideViewModel(
                rideDTO.id(),
                rideViewModel.user().updateCredit(rideDTO.credit()),
                rideViewModel.bike().updateBatteryLevel(rideDTO.charge()).updateLocation(rideDTO.x(), rideDTO.y())

        );
    }

    public static EBikeViewModel toDomain(EBikeDTOExt eBikeDTOExt) {
        EBikeViewModel.EBikeState state = EBikeViewModel.EBikeState.valueOf(eBikeDTOExt.state().toUpperCase());
        return new EBikeViewModel(eBikeDTOExt.id(), eBikeDTOExt.x(), eBikeDTOExt.y(), eBikeDTOExt.batteryLevel(), state, eBikeDTOExt.color());
    }

    public static UserDTO toDTO(UserViewModel userViewModel) {
        return new UserDTO(userViewModel.id(), userViewModel.credit(), userViewModel.admin());
    }

    public static EBikeDTO toDTO(EBikeViewModel ebikeViewModel) {
        String state = ebikeViewModel.state().name().toLowerCase();
        return new EBikeDTO(
                ebikeViewModel.id(),
                ebikeViewModel.x(),
                ebikeViewModel.y(),
                ebikeViewModel.batteryLevel(),
                state

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
