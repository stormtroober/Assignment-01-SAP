package sap.ass01.layered.presentation;

import sap.ass01.layered.presentation.models.EBikeViewModel;
import sap.ass01.layered.presentation.models.RideViewModel;
import sap.ass01.layered.services.Services.LoginService;
import sap.ass01.layered.services.Services.AdminService;
import sap.ass01.layered.services.Services.UserService;
import sap.ass01.layered.services.dto.EBikeDTO;
import sap.ass01.layered.services.impl.ServiceFactory;
import sap.ass01.layered.presentation.mapper.Mapper;
import sap.ass01.layered.presentation.models.UserViewModel;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class PresentationController {

    private final LoginService loginService = ServiceFactory.getLoginService();
    private final AdminService adminService = ServiceFactory.getAdminService();
    private final UserService userService = ServiceFactory.getUserService();

    // Login
    public void logIn(String username, Consumer<UserViewModel> onSuccess, Consumer<Throwable> onError) {
        loginService.logIn(username).subscribe(user -> {
            UserViewModel userViewModel = Mapper.toDomain(user); // Map the UserDTO to UserViewModel
            onSuccess.accept(userViewModel); // Call the success callback with the user view model
        }, error -> {
            onError.accept(error); // Call the error callback if the login fails
        });
    }

    //Register
    public void signUp(String username, boolean isAdmin, Runnable onSuccess, Consumer<Throwable> onError) {
        loginService.signUp(username, isAdmin)
                .subscribe(onSuccess::run, onError::accept);
    }

    /*---------------------Admin------------------------*/
    public void observeAllBikes(Consumer<Collection<EBikeDTO>> onSuccess, Consumer<Throwable> onError) {
        adminService.observeAllBikes().subscribe(
                onSuccess::accept,
                onError::accept
        );
    }

    public void observeAllUsers(Consumer<List<UserViewModel>> onSuccess, Consumer<Throwable> onError) {
        adminService.observeAllUsers().subscribe(users -> {
            List<UserViewModel> userViewModels = users.stream()
                    .map(Mapper::toDomain)
                    .collect(Collectors.toList());
            onSuccess.accept(userViewModels);
        }, onError::accept);
    }

    public void refreshAllBikes() {adminService.refreshAllBikes();}

    public void createEBike(String id, double xCoord, double yCoord, Consumer<EBikeViewModel> onSuccess, Consumer<Throwable> onError) {
        adminService.createEBike(id, xCoord, yCoord).subscribe(ebikeDTO -> {
            EBikeViewModel ebikeViewModel = Mapper.toDomain(ebikeDTO);
            onSuccess.accept(ebikeViewModel);
        }, onError::accept);
    }

    public void rechargeEBike(String id, Consumer<EBikeViewModel> onSuccess, Consumer<Throwable> onError) {
        adminService.rechargeEBike(id).subscribe(ebikeDTO -> {
            EBikeViewModel ebikeViewModel = Mapper.toDomain(ebikeDTO);
            onSuccess.accept(ebikeViewModel);
        }, onError::accept);
    }

    // Method to observe available bikes
    public void observeAvailableBikes(Consumer<List<EBikeViewModel>> onSuccess, Consumer<Throwable> onError) {
        userService.observeAvailableBikes().subscribe(
                bikeDTOs -> {
                    // Convert DTOs to ViewModels before passing to the view
                    List<EBikeViewModel> bikeViewModels = bikeDTOs.stream()
                            .map(Mapper::toDomain) // Assuming Mapper.toDomain converts EBikeDTO to EBikeViewModel
                            .collect(Collectors.toList());
                    onSuccess.accept(bikeViewModels);
                },
                onError::accept
        );
    }

    public void endRide(String userId, String rideId, String bikeId, Runnable onSuccess, Consumer<Throwable> onError) {
        userService.endRide(userId, rideId, bikeId).subscribe(onSuccess::run, onError::accept);
    }

    public void startRide(String rideId, UserViewModel user, EBikeViewModel bike, Consumer<RideViewModel> onSuccess, Consumer<Throwable> onError) {
        //String rideId = UUID.randomUUID().toString(); // Generate a unique ride ID
        userService.startRide(user.id(), rideId, bike.id()).subscribe(
                rideDTO -> {
                    RideViewModel rideViewModel = Mapper.toDomain(rideDTO, new RideViewModel(rideId, user, bike));
                    onSuccess.accept(rideViewModel);
                },
                onError::accept
        );
    }

    public void observeRide(String rideId,UserViewModel user,EBikeViewModel bike , Consumer<RideViewModel> onUpdate, Consumer<Throwable> onError, Runnable onComplete) {
        userService.observeRide(user.id(), rideId).subscribe(
                rideDTO -> {
                    RideViewModel rideViewModel = Mapper.toDomain(rideDTO, new RideViewModel(rideId, user, bike));
                    onUpdate.accept(rideViewModel);
                },
                onError::accept,
                onComplete::run
        );
    }

    public void rechargeCredit(String userId, int creditAmount, Consumer<UserViewModel> onSuccess, Consumer<Throwable> onError) {
        userService.rechargeCredit(userId, creditAmount)
                .subscribe(
                        userDTO -> {
                            UserViewModel userViewModel = Mapper.toDomain(userDTO);
                            onSuccess.accept(userViewModel);
                        },
                        onError::accept
                );
    }
}






