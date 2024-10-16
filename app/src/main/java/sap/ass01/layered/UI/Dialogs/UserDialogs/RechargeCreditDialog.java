package sap.ass01.layered.UI.Dialogs.UserDialogs;

import sap.ass01.layered.UI.Dialogs.AbstractDialog;
import sap.ass01.layered.UI.Models.UserViewModel;
import sap.ass01.layered.services.Services.UserService;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RechargeCreditDialog extends AbstractDialog {

    private JTextField creditAmountField;
    private final UserService userService;
    private final UserViewModel user;

    public RechargeCreditDialog(JFrame parent, UserService userService, UserViewModel user) {
        super(parent, "Recharge Credit");
        this.userService = userService;
        this.user = user;
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
            /*userService.rechargeCredit(user.id(), Double.parseDouble(creditAmount))
                    .subscribe(
                            userDTO -> {
                                // Handle success
                                JOptionPane.showMessageDialog(this, "Credit recharged successfully: " + userDTO);
                                dispose();
                            },
                            throwable -> {
                                // Handle error
                                JOptionPane.showMessageDialog(this, "Error recharging credit: " + throwable.getMessage());
                            }
                    );*/
            dispose();
        }
    }
}