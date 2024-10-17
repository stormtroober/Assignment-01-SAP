package sap.ass01.layered.ui.dialogs.user;

import sap.ass01.layered.ui.dialogs.AbstractDialog;
import sap.ass01.layered.ui.models.UserViewModel;
import sap.ass01.layered.services.Services.UserService;
import sap.ass01.layered.ui.views.UserView;

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
            userService.rechargeCredit(user.id(), Integer.parseInt(creditAmount))
                    .subscribe(
                            updatedUser -> {
                                ((UserView) getParent()).updateCredit(updatedUser.credit());
                                dispose();
                            },
                            throwable -> {
                                // Handle error
                                JOptionPane.showMessageDialog(this, "Error recharging credit: " + throwable.getMessage());
                            }
                    );
        }
    }
}