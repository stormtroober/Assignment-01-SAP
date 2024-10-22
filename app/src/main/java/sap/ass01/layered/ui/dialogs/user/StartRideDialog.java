/*package sap.ass01.layered.ui.dialogs.user;

import sap.ass01.layered.ui.dialogs.AbstractDialog;
import sap.ass01.layered.ui.models.EBikeViewModel;
import sap.ass01.layered.ui.models.RideViewModel;
import sap.ass01.layered.ui.models.UserViewModel;
import sap.ass01.layered.services.Services.UserService;
import sap.ass01.layered.ui.views.UserView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Optional;
import java.util.UUID;

public class StartRideDialog extends AbstractDialog {

    private JTextField idEBikeField;
    private final UserService userService;
    private final UserViewModel user;

    public StartRideDialog(JFrame parent, UserService userService, UserViewModel user) {
        super(parent, "Start Riding an E-Bike");
        this.userService = userService;
        this.user = user;
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

            Optional<EBikeViewModel> bike = ((UserView) getParent()).findBike(bikeId);
            // Handle success
            if(bike.isPresent()) {
                log("*******************************************************");
                RideViewModel rideViewModel = new RideViewModel(rideId, user, bike.get());
                ((UserView) getParent()).initRide(rideViewModel);
                log(rideViewModel.toString());
            }

            userService.startRide(user.id(), rideId, bikeId)
                    .subscribe(
                            rideDTO -> {

                                observeRideUpdates(user.id(), rideId);
                                dispose();


                            },
                            throwable -> {
                                // Handle error
                                log("Error starting ride: " + throwable.getMessage());
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
                            log("Ride update: " + rideDTO);
                            ((UserView) getParent()).updateRide(rideDTO);
                        },
                        throwable -> {
                            // Handle error
                            System.err.println("Error observing ride: " + throwable.getMessage());
                        },
                        () -> {
                            // Handle completion
                            log("Ride completed");
                            ((UserView) getParent()).endRide();
                        }
                );
    }

    private void log(String msg){
        System.out.println("[StartRideDialog] "+msg);
    }
}*/