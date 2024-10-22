package sap.ass01.frontend.dialogs.admin;

import sap.ass01.layered.services.Services.AdminService;
import sap.ass01.frontend.dialogs.AbstractDialog;
import sap.ass01.layered.presentation.PresentationController;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AddEBikeDialog extends AbstractDialog {

    private JTextField idField;
    private JTextField xCoordField;
    private JTextField yCoordField;
    private final PresentationController presentationController = new PresentationController();

    public AddEBikeDialog(JFrame parent, PresentationController adminService) {
        super(parent, "Adding E-Bike");
        setupDialog();
    }

    private void setupDialog() {
        idField = new JTextField();
        xCoordField = new JTextField();
        yCoordField = new JTextField();

        addField("E-Bike ID:", idField);
        addField("E-Bike location - X coord:", xCoordField);
        addField("E-Bike location - Y coord:", yCoordField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if (e.getSource() == confirmButton) {
            String id = idField.getText();
            double xCoord = Double.parseDouble(xCoordField.getText());
            double yCoord = Double.parseDouble(yCoordField.getText());
            // Handle the addition of the e-bike here
            presentationController.createEBike(id, xCoord, yCoord, ebikeViewModel -> {
                JOptionPane.showMessageDialog(this, "E-Bike added successfully: " + ebikeViewModel);
                dispose();
            }, throwable -> {
                JOptionPane.showMessageDialog(this, "Error adding E-Bike: " + throwable.getMessage());
            });
            /*adminService.createEBike(id, xCoord, yCoord)
                    .subscribe(
                            ebikeDTO -> {
                                // Handle success
                                JOptionPane.showMessageDialog(this, "E-Bike added successfully: " + ebikeDTO);
                                dispose();
                            },
                            throwable -> {
                                // Handle error
                                JOptionPane.showMessageDialog(this, "Error adding E-Bike: " + throwable.getMessage());
                            }
                    );*/
            dispose();
        }
    }
}