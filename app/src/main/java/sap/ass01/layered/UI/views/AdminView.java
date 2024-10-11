package sap.ass01.layered.UI.views;

import sap.ass01.layered.UI.Dialogs.AdminDialogs.AddEBikeDialog;
import sap.ass01.layered.UI.Dialogs.AdminDialogs.RechargeBikeDialog;
import sap.ass01.layered.services.Services.AdminService;
import sap.ass01.layered.services.impl.ServiceFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminView extends AbstractView {

    private final AdminService adminService = ServiceFactory.getAdminService();

    public AdminView() {
        super("Admin View");
        setupView();
        refreshView();
    }

    private void setupView() {
        // Use the existing topPanel from AbstractView
        topPanel.setLayout(new FlowLayout());

        JButton addBikeButton = new JButton("Add Bike");
        addBikeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEBikeDialog addEBikeDialog = new AddEBikeDialog(AdminView.this);
                addEBikeDialog.setVisible(true);
            }
        });
        topPanel.add(addBikeButton);

        JButton rechargeBikeButton = new JButton("Recharge Bike");
        rechargeBikeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RechargeBikeDialog rechargeBikeDialog = new RechargeBikeDialog(AdminView.this);
                rechargeBikeDialog.setVisible(true);
            }
        });
        topPanel.add(rechargeBikeButton);

    }


    public void refreshView() {
        updateVisualizerPanel(true);
    }
}