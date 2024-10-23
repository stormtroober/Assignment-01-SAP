package sap.ass01.hexagonal.infrastructure.presentation;

import sap.ass01.hexagonal.infrastructure.adapters.view.ViewAdapter;
import sap.ass01.hexagonal.infrastructure.presentation.mapper.Mapper;
import sap.ass01.hexagonal.infrastructure.presentation.models.EBikeViewModel;
import sap.ass01.hexagonal.infrastructure.presentation.models.RideViewModel;
import sap.ass01.hexagonal.infrastructure.presentation.models.UserViewModel;
import sap.ass01.hexagonal.application.ports.entities.EBikeDTO;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class PresentationController {

    private final ViewAdapter viewAdapter;

    public PresentationController(ViewAdapter viewAdapter) {
        this.viewAdapter = viewAdapter;
    }

    public void logIn(String username, Consumer<UserViewModel> onSuccess, Consumer<Throwable> onError) {
        viewAdapter.logIn(username).subscribe(userDTO -> {
            UserViewModel userViewModel = Mapper.toDomain(userDTO); // Convert from DTO to ViewModel
            onSuccess.accept(userViewModel);
        }, onError::accept);
    }

// Register
    public void signUp(String username, boolean isAdmin, Runnable onSuccess, Consumer<Throwable> onError) {
        viewAdapter.signUp(username, isAdmin).subscribe(onSuccess::run, onError::accept);
    }

// Admin
    public void observeAllBikes(Consumer<Collection<EBikeDTO>> onSuccess, Consumer<Throwable> onError) {
        viewAdapter.observeAllBikes().subscribe(
            onSuccess::accept,
            onError::accept
    );
}

    public void observeAllUsers(Consumer<List<UserViewModel>> onSuccess, Consumer<Throwable> onError) {
        viewAdapter.observeAllUsers().subscribe(users -> {
            List<UserViewModel> userViewModels = users.stream()
                    .map(Mapper::toDomain) // Convert from DTO to ViewModel
                    .collect(Collectors.toList());
            onSuccess.accept(userViewModels);
        }, onError::accept);
    }

    public void refreshAllBikes() { viewAdapter.refreshAllBikes();}

    public void createEBike(String id, double xCoord, double yCoord, Consumer<EBikeViewModel> onSuccess, Consumer<Throwable> onError) {
        viewAdapter.createEBike(id, xCoord, yCoord).subscribe(ebikeDTO -> {
            EBikeViewModel ebikeViewModel = Mapper.toDomain(ebikeDTO); // Convert from DTO to ViewModel
            onSuccess.accept(ebikeViewModel);
        }, onError::accept);
    }

    public void rechargeEBike(String id, Consumer<EBikeViewModel> onSuccess, Consumer<Throwable> onError) {
        viewAdapter.rechargeEBike(id).subscribe(ebikeDTO -> {
            EBikeViewModel ebikeViewModel = Mapper.toDomain(ebikeDTO); // Convert from DTO to ViewModel
            onSuccess.accept(ebikeViewModel);
        }, onError::accept);

    }


}