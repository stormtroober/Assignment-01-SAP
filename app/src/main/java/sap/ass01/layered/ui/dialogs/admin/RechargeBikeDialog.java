package sap.ass01.layered.ui.dialogs.admin;

import sap.ass01.layered.ui.dialogs.AbstractDialog;
import sap.ass01.layered.services.Services.AdminService;
import sap.ass01.layered.services.dto.EBikeDTO;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RechargeBikeDialog extends AbstractDialog {

    private JTextField idField;
    private final AdminService adminService;

    public RechargeBikeDialog(JFrame parent, AdminService adminService) {
        super(parent, "Recharge Bike");
        this.adminService = adminService;
        setupDialog();
    }

    private void setupDialog() {
        idField = new JTextField();
        addField("E-Bike ID to recharge:", idField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if (e.getSource() == confirmButton) {
            String id = idField.getText();
            adminService.rechargeEBike(id)
                    .subscribe(
                            ebikeDTO -> {
                                // Handle success
                                JOptionPane.showMessageDialog(this, "E-Bike recharged successfully: " + ebikeDTO);
                                dispose();
                            },
                            throwable -> {
                                // Handle error
                                JOptionPane.showMessageDialog(this, "Error recharging E-Bike: " + throwable.getMessage());
                            }
                    );
        }
    }
}