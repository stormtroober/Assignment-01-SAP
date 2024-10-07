package sap.ass01.layered.UI.views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserView extends BaseView {

    public UserView() {
        super("User View");
        setupView();
    }

    private void setupView() {
        addTopPanelButton("Start Ride", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle start ride action
                JOptionPane.showMessageDialog(UserView.this, "Start Ride button clicked");
            }
        });

        addTopPanelButton("Recharge Credit", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle recharge credit action
                JOptionPane.showMessageDialog(UserView.this, "Recharge Credit button clicked");
            }
        });
    }
}