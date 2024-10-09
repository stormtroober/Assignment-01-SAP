package sap.ass01.layered.UI.Dialogs.AdminDialogs;

import sap.ass01.layered.UI.Dialogs.AbstractDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RechargeBikeDialog extends AbstractDialog {

    private JTextField idField;

    public RechargeBikeDialog(JFrame parent) {
        super(parent, "Recharge Bike");
        setupDialog();
    }

    private void setupDialog() {
        idField = new JTextField();
        addField("E-Bike ID:", idField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if (e.getSource() == confirmButton) {
            String id = idField.getText();
            // Handle the recharge bike logic here
            dispose();
        }
    }
}