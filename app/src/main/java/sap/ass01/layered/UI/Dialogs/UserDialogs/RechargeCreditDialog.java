package sap.ass01.layered.UI.Dialogs.UserDialogs;

import sap.ass01.layered.UI.Dialogs.AbstractDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RechargeCreditDialog extends AbstractDialog {

    private JTextField creditAmountField;

    public RechargeCreditDialog(JFrame parent) {
        super(parent, "Recharge Credit");
        setupDialog();
    }

    private void setupDialog() {
        creditAmountField = new JTextField();
        addField("Credit Amount:", creditAmountField);
        confirmButton.setText("Recharge");
        cancelButton.setText("Cancel");
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if (e.getSource() == confirmButton) {
            String creditAmount = creditAmountField.getText();
            // Handle the recharge credit logic here
            dispose();
        }
    }
}