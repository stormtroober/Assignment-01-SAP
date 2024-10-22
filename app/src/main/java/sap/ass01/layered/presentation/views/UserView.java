package sap.ass01.layered.presentation.views;

import sap.ass01.frontend.dialogs.user.RechargeCreditDialog;
import sap.ass01.frontend.dialogs.user.StartRideDialog;
import sap.ass01.frontend.models.EBikeViewModel;
import sap.ass01.frontend.models.RideViewModel;
import sap.ass01.frontend.models.UserViewModel;
import sap.ass01.layered.presentation.PresentationController;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

public class UserView extends AbstractView {

    //private final UserService userService = ServiceFactory.getUserService();
    private Optional<RideViewModel> ongoingRide = Optional.empty();
    private JButton rideButton;
    private final PresentationController presentationController = new PresentationController();

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
        });*/
        rideButton = new JButton("Start Ride");
        rideButton.addActionListener(e -> toggleRide());
        buttonPanel.add(rideButton);

        addTopPanelButton("Recharge Credit", e -> {
            RechargeCreditDialog rechargeCreditDialog = new RechargeCreditDialog(UserView.this, presentationController, actualUser);
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
        StartRideDialog startRideDialog = new StartRideDialog(UserView.this, presentationController, actualUser);
        startRideDialog.setVisible(true);
    }

    private void stopRide() {
        ongoingRide.ifPresent(ride -> {
            presentationController.endRide(actualUser.id(), ride.id(), ride.bike().id(),
                    this::endRide,
                    throwable -> {
                        log("Error ending ride: " + throwable.getMessage());
                        throwable.printStackTrace();
                    });
                    /*.subscribe(
                            this::endRide,
                            throwable -> {
                                log("Error ending ride: " + throwable.getMessage());
                                throwable.printStackTrace();
                            }
                    );*/
        });
    }

    private void updateRideButtonState() {
        rideButton.setText(ongoingRide.isPresent() ? "Stop Ride" : "Start Ride");
    }

    private void observeAvailableBikes() {
        presentationController.observeAvailableBikes(
                this::updateAvailableBikes,
                throwable -> {
                    log("Error observing available bikes: " + throwable.getMessage());
                    throwable.printStackTrace();
                }
        );
        /*userService.observeAvailableBikes()
                .subscribe(
                        this::updateAvailableBikes,
                        throwable -> {
                            // Handle error
                            log("Error observing available bikes: " + throwable.getMessage());
                            throwable.printStackTrace(); // Print stack trace for detailed debugging
                        }
                );*/
    }

    private void updateAvailableBikes(List<EBikeViewModel> availableBikes) {
        try {
            log("Updating available bikes: " + availableBikes);
            Optional<EBikeViewModel> ongoingBike = ongoingRide.map(RideViewModel::bike);

            // Create the updated bike list
            eBikes = availableBikes;

            // Add the ongoing bike if present
            ongoingBike.ifPresent(eBikes::add);

            log("Available bikes updated: " + eBikes);
            updateVisualizerPanel();
            /*log("Updating available bikes: " + availableBikes);
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
            updateVisualizerPanel();*/
        } catch (Exception e) {
            log("Exception while updating available bikes: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for detailed debugging
        }
    }

    public void initRide(RideViewModel ride){
        this.ongoingRide = Optional.of(ride);
        updateRideButtonState();
    }
    public void updateRide(RideViewModel ride) {
        log("My ride prev ->" + eBikes.toString());
        if(ongoingRide.isPresent()){
            log("On going is present");
            ongoingRide = Optional.of(ride);
            //ongoingRide = Optional.of(Mapper.toDomain(rideDTO, ongoingRide.get()));
            log("Value of ongoing ride ->"+ongoingRide.get().toString());
            eBikes = eBikes.stream()
                    .map(bike -> bike.id().equals(ongoingRide.get().bike().id()) ? ongoingRide.get().bike() : bike)
                    .toList();

        }
        log("Ride updated: " + ride);
        log("My ride ->" + eBikes.toString());
        // Call a method to refresh the visual representation
        updateVisualizerPanel();
        updateRideButtonState();
        updateCredit(ride.user().credit());
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
}