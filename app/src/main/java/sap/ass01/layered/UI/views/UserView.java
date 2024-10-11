package sap.ass01.layered.UI.views;

import sap.ass01.layered.UI.Dialogs.UserDialogs.RechargeCreditDialog;
import sap.ass01.layered.UI.Dialogs.UserDialogs.StartRideDialog;
import sap.ass01.layered.services.Services.UserService;
import sap.ass01.layered.services.dto.EBikeDTO;
import sap.ass01.layered.services.dto.RideDTO;
import sap.ass01.layered.services.impl.ServiceFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

public class UserView extends AbstractView {

    private final UserService userService = ServiceFactory.getUserService();

    public UserView() {
        super("User View");
        setupView();
        observeAvailableBikes();
        refreshView();
    }

    private void setupView() {
        addTopPanelButton("Start Ride", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartRideDialog startRideDialog = new StartRideDialog(UserView.this, userService);
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
        log("Available bikes updated: " + availableBikes);
        // Call a method to refresh the visual representation
        refreshView();
    }

    public void updateRide(RideDTO rideDTO) {
        // Update the UI components with the new ride data
        log("Ride updated: " + rideDTO);
        // Call a method to refresh the visual representation
        refreshView();
    }

    public void refreshView() {
        updateVisualizerPanel(false);
    }

    private void log(String msg){
        System.out.println("[UserView] " + msg);
    }
}