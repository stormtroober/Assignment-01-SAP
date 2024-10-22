package sap.ass01.layered.presentation.dialogs.user;

import sap.ass01.layered.presentation.models.EBikeViewModel;
import sap.ass01.layered.presentation.models.UserViewModel;
import sap.ass01.layered.presentation.views.UserView;
import sap.ass01.layered.presentation.PresentationController;
import sap.ass01.layered.presentation.dialogs.AbstractDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Optional;
import java.util.UUID;

public class StartRideDialog extends AbstractDialog {

    private JTextField idEBikeField;
    private final PresentationController presentationController;
    private final UserViewModel user;

    public StartRideDialog(JFrame parent, PresentationController presentationController, UserViewModel user) {
        super(parent, "Start Riding an E-Bike");
        this.presentationController = presentationController;
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
                presentationController.startRide(rideId, user, bike.get(),
                        startedRide -> {
                            ((UserView) getParent()).initRide(startedRide);
                            observeRideUpdates(rideId, user, bike.get()); // Subscribe to ride updates
                            dispose(); // Close the dialog after starting the ride
                        },
                        throwable -> {
                            log("Error starting ride: " + throwable.getMessage());
                            JOptionPane.showMessageDialog(this, "Error starting ride: " + throwable.getMessage());
                        }
                );
            } else {
                log("Bike not found");
                JOptionPane.showMessageDialog(this, "Bike not found");
            }



            /*presentationController.startRide(user.id(), rideId, bikeId)
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
                    );*/
        }
    }

    private void observeRideUpdates(String rideId, UserViewModel user, EBikeViewModel bike) {
        presentationController.observeRide(rideId, user, bike,
                rideViewModel -> {
                    // Update the view with the new ride ViewModel
                    log("Ride update: " + rideViewModel);
                    ((UserView) getParent()).updateRide(rideViewModel);
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

                /*.subscribe(
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
                );*/
    }

    private void log(String msg){
        System.out.println("[StartRideDialog] "+msg);
    }
}