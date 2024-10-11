package sap.ass01.layered.UI.Dialogs.UserDialogs;

import sap.ass01.layered.UI.Dialogs.AbstractDialog;
import sap.ass01.layered.services.Services.UserService;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class StartRideDialog extends AbstractDialog {

    private JTextField idEBikeField;
    private final UserService userService;

    public StartRideDialog(JFrame parent, UserService userService) {
        super(parent, "Start Riding an E-Bike");
        this.userService = userService;
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
            // Handle the start ride logic here
//            userService.startRide(bikeId)
//                    .subscribe(
//                            rideDTO -> {
//                                // Handle success
//                                JOptionPane.showMessageDialog(this, "Ride started successfully: " + rideDTO);
//                                dispose();
//                            },
//                            throwable -> {
//                                // Handle error
//                                JOptionPane.showMessageDialog(this, "Error starting ride: " + throwable.getMessage());
//                            }
//                    );
//            dispose();
        }
    }
}