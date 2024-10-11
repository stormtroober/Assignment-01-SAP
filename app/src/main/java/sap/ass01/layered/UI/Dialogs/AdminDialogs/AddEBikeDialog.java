package sap.ass01.layered.UI.Dialogs.AdminDialogs;

import sap.ass01.layered.UI.Dialogs.AbstractDialog;
import sap.ass01.layered.services.Services.AdminService;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AddEBikeDialog extends AbstractDialog {

    private JTextField idField;
    private JTextField xCoordField;
    private JTextField yCoordField;
    private final AdminService adminService;

    public AddEBikeDialog(JFrame parent, AdminService adminService) {
        super(parent, "Adding E-Bike");
        this.adminService = adminService;
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
            adminService.createEBike(id, xCoord, yCoord)
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
                    );
            dispose();
        }
    }
}