package sap.ass01.layered.UI.views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminView extends BaseView {

    public AdminView() {
        super("Admin View");
        setupView();
    }

    private void setupView() {
        addTopPanelButton("Add Bike", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle add bike action
                JOptionPane.showMessageDialog(AdminView.this, "Add Bike button clicked");
            }
        });

        addTopPanelButton("Recharge Bike", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle recharge bike action
                JOptionPane.showMessageDialog(AdminView.this, "Recharge Bike button clicked");
            }
        });
    }
}