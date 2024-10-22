/*package sap.ass01.layered.ui.views;

import sap.ass01.layered.ui.dialogs.user.RechargeCreditDialog;
import sap.ass01.layered.ui.dialogs.user.StartRideDialog;
import sap.ass01.layered.ui.mapper.Mapper;
import sap.ass01.layered.ui.models.EBikeViewModel;
import sap.ass01.layered.ui.models.RideViewModel;
import sap.ass01.layered.ui.models.UserViewModel;
import sap.ass01.layered.services.Services.UserService;
import sap.ass01.layered.services.dto.EBikeDTO;
import sap.ass01.layered.services.dto.RideDTO;
import sap.ass01.layered.services.impl.ServiceFactory;

import javax.swing.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserView extends AbstractView {

    private final UserService userService = ServiceFactory.getUserService();
    private Optional<RideViewModel> ongoingRide = Optional.empty();
    private JButton rideButton;

    public UserView(UserViewModel user) {
        super("User View", user);
        setupView();
        observeAvailableBikes();
        updateVisualizerPanel();
    }

    private void setupView() {
        /*addTopPanelButton("Start Ride", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartRideDialog startRideDialog = new StartRideDialog(UserView.this, userService, actualUser);
                startRideDialog.setVisible(true);
            }
        });

        addTopPanelButton("Recharge Credit", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RechargeCreditDialog rechargeCreditDialog = new RechargeCreditDialog(UserView.this, userService, actualUser);
                rechargeCreditDialog.setVisible(true);
            }
        });
        rideButton = new JButton("Start Ride");
        rideButton.addActionListener(e -> toggleRide());
        buttonPanel.add(rideButton);

        addTopPanelButton("Recharge Credit", e -> {
            RechargeCreditDialog rechargeCreditDialog = new RechargeCreditDialog(UserView.this, userService, actualUser);
            rechargeCreditDialog.setVisible(true);
        });
        updateRideButtonState();
    }

    private void toggleRide() {
        if (ongoingRide.isPresent()) {
            stopRide();
        } else {
            startRide();
        }
    }

    private void startRide() {
        StartRideDialog startRideDialog = new StartRideDialog(UserView.this, userService, actualUser);
        startRideDialog.setVisible(true);
    }

    private void stopRide() {
        ongoingRide.ifPresent(ride -> {
            userService.endRide(actualUser.id(), ride.id(), ride.bike().id())
                    .subscribe(
                            this::endRide,
                            throwable -> {
                                log("Error ending ride: " + throwable.getMessage());
                                throwable.printStackTrace();
                            }
                    );
        });
    }

    private void updateRideButtonState() {
        rideButton.setText(ongoingRide.isPresent() ? "Stop Ride" : "Start Ride");
    }

    private void observeAvailableBikes() {
        userService.observeAvailableBikes()
                .subscribe(
                        this::updateAvailableBikes,
                        throwable -> {
                            // Handle error
                            log("Error observing available bikes: " + throwable.getMessage());
                            throwable.printStackTrace(); // Print stack trace for detailed debugging
                        }
                );
    }

    private void updateAvailableBikes(Collection<EBikeDTO> availableBikes) {
        try {
            log("Updating available bikes: " + availableBikes);
            Optional<EBikeViewModel> ongoingBike = ongoingRide.map(RideViewModel::bike);

            // Create a mutable list from the stream
            List<EBikeViewModel> updatedBikes = availableBikes.stream()
                    .map(Mapper::toDomain)
                    .collect(Collectors.toList());

            // Add the ongoing bike if present
            ongoingBike.ifPresent(updatedBikes::add);

            eBikes = updatedBikes;
            log("Available bikes updated: " + eBikes);
            // Call a method to refresh the visual representation
            updateVisualizerPanel();
        } catch (Exception e) {
            log("Exception while updating available bikes: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for detailed debugging
        }
    }

    public void initRide(RideViewModel ride){
        this.ongoingRide = Optional.of(ride);
        updateRideButtonState();
    }
    public void updateRide(RideDTO rideDTO) {
        log("My ride prev ->" + eBikes.toString());
        if(ongoingRide.isPresent()){
            log("On going is present");
            ongoingRide = Optional.of(Mapper.toDomain(rideDTO, ongoingRide.get()));
            log("Value of ongoing ride ->"+ongoingRide.get().toString());
            eBikes = eBikes.stream()
                    .map(bike -> bike.id().equals(ongoingRide.get().bike().id()) ? ongoingRide.get().bike() : bike)
                    .toList();

        }
        log("Ride updated: " + rideDTO);
        log("My ride ->" + eBikes.toString());
        // Call a method to refresh the visual representation
        updateVisualizerPanel();
        updateRideButtonState();
        updateCredit(rideDTO.credit());
    }

    public Optional<EBikeViewModel> findBike(String bikeId) {
        return eBikes.stream()
                .filter(bike -> bike.id().equals(bikeId))
                .findFirst();
    }

    private void log(String msg){
        System.out.println("[UserView] " + msg);
    }

    public void endRide() {
        ongoingRide = Optional.empty();
        updateVisualizerPanel();
        updateRideButtonState();
    }

    public void updateCredit(int credit){
        actualUser = actualUser.updateCredit(credit);
        updateVisualizerPanel();
    }
}*/