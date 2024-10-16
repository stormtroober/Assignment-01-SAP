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
import java.util.Optional;

public class UserView extends AbstractView {

    private final UserService userService = ServiceFactory.getUserService();
    private Optional<RideViewModel> ongoingRide = Optional.empty();

    public UserView(UserViewModel user) {
        super("User View", user);
        setupView();
        observeAvailableBikes();
        updateVisualizerPanel();
    }

    private void setupView() {
        addTopPanelButton("Start Ride", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartRideDialog startRideDialog = new StartRideDialog(UserView.this, userService, actualUser);
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
        Optional<EBikeViewModel> ongoingBike = ongoingRide.map(RideViewModel::bike);

        eBikes = availableBikes.stream()
                .map(Mapper::toDomain)
                .filter(bike -> ongoingBike.map(ongoing -> !ongoing.id().equals(bike.id())).orElse(true))
                .toList();


        log("Available bikes updated: " + availableBikes);
        // Call a method to refresh the visual representation
        updateVisualizerPanel();
    }

    public void initRide(RideViewModel ride){
        this.ongoingRide = Optional.of(ride);
    }
    public void updateRide(RideDTO rideDTO) {

        if(ongoingRide.isPresent()){
            ongoingRide = Optional.of(Mapper.toDomain(rideDTO, ongoingRide.get()));

        }


        log("Ride updated: " + rideDTO);

        log("My ride ->" + eBikes.toString());
        // Call a method to refresh the visual representation
        updateVisualizerPanel();
    }

    public Optional<EBikeViewModel> findBike(String bikeId) {
        return eBikes.stream()
                .filter(bike -> bike.id().equals(bikeId))
                .findFirst();
    }

    private void log(String msg){
        System.out.println("[UserView] " + msg);
    }
}