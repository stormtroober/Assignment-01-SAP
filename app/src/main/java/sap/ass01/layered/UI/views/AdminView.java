package sap.ass01.layered.UI.views;

import sap.ass01.layered.UI.Dialogs.AdminDialogs.AddEBikeDialog;
import sap.ass01.layered.UI.Dialogs.AdminDialogs.RechargeBikeDialog;
import sap.ass01.layered.services.Services.AdminService;
import sap.ass01.layered.services.dto.EBikeDTO;
import sap.ass01.layered.services.impl.ServiceFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class AdminView extends AbstractView {

    private final AdminService adminService = ServiceFactory.getAdminService();

    public AdminView() {
        super("Admin View");
        setupView();
        observeAllBikes();
        refreshView();
    }

    private void setupView() {
        topPanel.setLayout(new FlowLayout());

        JButton addBikeButton = new JButton("Add Bike");
        addBikeButton.addActionListener(e -> {
            AddEBikeDialog addEBikeDialog = new AddEBikeDialog(AdminView.this, adminService);
            addEBikeDialog.setVisible(true);
        });
        topPanel.add(addBikeButton);

        JButton rechargeBikeButton = new JButton("Recharge Bike");
        rechargeBikeButton.addActionListener(e -> {
            RechargeBikeDialog rechargeBikeDialog = new RechargeBikeDialog(AdminView.this);
            rechargeBikeDialog.setVisible(true);
        });
        topPanel.add(rechargeBikeButton);
    }

    private void observeAllBikes() {
        adminService.observeAllBikes()
                .subscribe(
                        this::updateAllBikes,
                        throwable -> {
                            // Handle error
                            System.err.println("Error observing available bikes: " + throwable.getMessage());
                        }
                );
    }

    private void updateAllBikes(Collection<EBikeDTO> allBikes) {
        // Update the UI components with the new available bikes data
        System.out.println("Available bikes updated: " + allBikes);
        // Call a method to refresh the visual representation
        refreshView();
    }

    public void refreshView() {
        updateVisualizerPanel(true);
    }
}