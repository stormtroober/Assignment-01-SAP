package sap.ass01.frontend.dialogs.admin;

import sap.ass01.layered.services.Services.AdminService;
import sap.ass01.frontend.dialogs.AbstractDialog;
import sap.ass01.layered.presentation.PresentationController;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RechargeBikeDialog extends AbstractDialog {

    private JTextField idField;
    //private final AdminService adminService;
    private final PresentationController presentationController = new PresentationController();

    public RechargeBikeDialog(JFrame parent, PresentationController presentationController) {
        super(parent, "Recharge Bike");
        //this.adminService = adminService;
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
            presentationController.rechargeEBike(id, ebikeViewModel -> {
                JOptionPane.showMessageDialog(this, "E-Bike recharged successfully: " + ebikeViewModel);
                dispose();
            }, throwable -> {
                JOptionPane.showMessageDialog(this, "Error recharging E-Bike: " + throwable.getMessage());
            });
            /*adminService.rechargeEBike(id)
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
                    );*/
        }
    }
}