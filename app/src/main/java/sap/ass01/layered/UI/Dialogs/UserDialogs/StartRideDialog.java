package sap.ass01.layered.UI.Dialogs.UserDialogs;

import sap.ass01.layered.UI.Dialogs.AbstractDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class StartRideDialog extends AbstractDialog {

    private JTextField idEBikeField;

    public StartRideDialog(JFrame parent) {
        super(parent, "Start Riding an E-Bike");
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
            dispose();
        }
    }
}