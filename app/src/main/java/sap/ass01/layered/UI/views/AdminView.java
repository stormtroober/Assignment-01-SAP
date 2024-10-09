package sap.ass01.layered.UI.views;

import sap.ass01.layered.UI.Dialogs.AdminDialogs.AddEBikeDialog;
import sap.ass01.layered.UI.Dialogs.AdminDialogs.RechargeBikeDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminView extends AbstractView {

    public AdminView() {
        super("Admin View");
        setupView();
    }

    private void setupView() {
        addTopPanelButton("Add Bike", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEBikeDialog addEBikeDialog = new AddEBikeDialog(AdminView.this);
                addEBikeDialog.setVisible(true);
            }
        });

        addTopPanelButton("Recharge Bike", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RechargeBikeDialog rechargeBikeDialog = new RechargeBikeDialog(AdminView.this);
                rechargeBikeDialog.setVisible(true);
            }
        });
    }
}