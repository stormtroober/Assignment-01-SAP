package sap.ass01.layered.UI.Dialogs.UserDialogs;

import sap.ass01.layered.UI.Dialogs.AbstractDialog;
import sap.ass01.layered.UI.Models.UserViewModel;
import sap.ass01.layered.services.Services.UserService;
import sap.ass01.layered.services.dto.RideDTO;
import sap.ass01.layered.UI.views.UserView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.UUID;

public class StartRideDialog extends AbstractDialog {

    private JTextField idEBikeField;
    private final UserService userService;
    private final String userId;

    public StartRideDialog(JFrame parent, UserService userService, String userId) {
        super(parent, "Start Riding an E-Bike");
        this.userService = userService;
        this.userId = userId;
        setupDialog();
    }

    private void setupDialog() {
        idEBikeField = new JTextField();
        addField("E-Bike ID:", idEBikeField);
        confirmButton.setText("Start Ride");
        cancelButton.setText("Cancel");
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if (e.getSource() == confirmButton) {
            String bikeId = idEBikeField.getText();
            //String userId = "u"; // Replace with actual user ID
            String rideId = UUID.randomUUID().toString(); // Generate a unique ride ID

            System.out.println("Starting ride with userId: " + userId + ", bikeId: " + bikeId + ", rideId: " + rideId);

            userService.startRide(userId, rideId, bikeId)
                    .subscribe(
                            rideDTO -> {
                                // Handle success
                                System.out.println("Ride started successfully: " + rideDTO);
                                observeRideUpdates(userId, rideId);
                                dispose();
                            },
                            throwable -> {
                                // Handle error
                                System.err.println("Error starting ride: " + throwable.getMessage());
                                JOptionPane.showMessageDialog(this, "Error starting ride: " + throwable.getMessage());
                            }
                    );
        }
    }

    private void observeRideUpdates(String userId, String rideId) {
        userService.observeRide(userId, rideId)
                .subscribe(
                        rideDTO -> {
                            // Update the view with the new ride DTO
                            System.out.println("Ride update: " + rideDTO);
                            ((UserView) getParent()).updateRide(rideDTO);
                        },
                        throwable -> {
                            // Handle error
                            System.err.println("Error observing ride: " + throwable.getMessage());
                        }
                );
    }
}