package sap.ass01.layered.UI.Dialogs.AdminDialogs;

import sap.ass01.layered.UI.Dialogs.AbstractDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AddEBikeDialog extends AbstractDialog {

    private JTextField idField;
    private JTextField xCoordField;
    private JTextField yCoordField;

    public AddEBikeDialog(JFrame parent) {
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
            dispose();
        }
    }
}