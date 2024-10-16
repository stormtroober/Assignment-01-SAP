package sap.ass01.layered.UI.views;

import sap.ass01.layered.UI.Dialogs.UserDialogs.RechargeCreditDialog;
import sap.ass01.layered.UI.Dialogs.UserDialogs.StartRideDialog;
import sap.ass01.layered.UI.Mapper.Mapper;
import sap.ass01.layered.UI.Models.EBikeViewModel;
import sap.ass01.layered.UI.Models.RideViewModel;
import sap.ass01.layered.UI.Models.UserViewModel;
import sap.ass01.layered.services.Services.UserService;
import sap.ass01.layered.services.dto.EBikeDTO;
import sap.ass01.layered.services.dto.RideDTO;
import sap.ass01.layered.services.impl.ServiceFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;

public class UserView extends AbstractView {

    private final UserService userService = ServiceFactory.getUserService();
    private final String userId;
    private RideViewModel ongoingRide;

    public UserView(String userId) {
        super("User View");
        this.userId = userId;
        setupView();
        observeAvailableBikes();
        refreshView();
    }

    private void setupView() {
        addTopPanelButton("Start Ride", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartRideDialog startRideDialog = new StartRideDialog(UserView.this, userService, userId);
                startRideDialog.setVisible(true);
            }
        });

        addTopPanelButton("Recharge Credit", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RechargeCreditDialog rechargeCreditDialog = new RechargeCreditDialog(UserView.this);
                rechargeCreditDialog.setVisible(true);
            }
        });
    }

    private void observeAvailableBikes() {
        userService.observeAvailableBikes()
                .subscribe(
                        this::updateAvailableBikes,
                        throwable -> {
                            // Handle error
                            System.err.println("Error observing available bikes: " + throwable.getMessage());
                        }
                );
    }

    private void updateAvailableBikes(Collection<EBikeDTO> availableBikes) {
        // Update the UI components with the new available bikes data
        // Convert EBikeDTOs to ViewModels
        List<EBikeViewModel> bikeModels = availableBikes.stream()
                .map(Mapper::toDomain)
                .toList();

         log("Available bikes updated: " + availableBikes);
        // Call a method to refresh the visual representation
        updateVisualizerPanel(false, bikeModels, List.of());
        refreshView();
    }

    public void updateRide(RideDTO rideDTO) {
        // Update the UI components with the new ride data
        if (ongoingRide == null){
           UserViewModel user = new UserViewModel(userId, 0, false);
           EBikeViewModel eBike = new EBikeViewModel(rideDTO.id(), rideDTO.x(), rideDTO.y(), rideDTO.charge(), "AVAILABLE");
           ongoingRide = new RideViewModel(rideDTO.id(), user, eBike);
        } else {
            ongoingRide = Mapper.toDomain(rideDTO, ongoingRide);
        }
        updateVisualizerPanel(false, eBikes, users);
        log("Ride updated: " + rideDTO);

        // Call a method to refresh the visual representation
        refreshView();
    }

    public void refreshView() {
        updateVisualizerPanel(false, eBikes, users);
    }

    private void log(String msg){
        System.out.println("[UserView] " + msg);
    }
}