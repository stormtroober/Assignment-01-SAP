package sap.ass01.layered.UI.views;

import sap.ass01.layered.UI.Dialogs.AdminDialogs.AddEBikeDialog;
import sap.ass01.layered.UI.Dialogs.AdminDialogs.RechargeBikeDialog;
import sap.ass01.layered.UI.Models.UserViewModel;
import sap.ass01.layered.services.Services.AdminService;
import sap.ass01.layered.services.dto.EBikeDTO;
import sap.ass01.layered.services.impl.ServiceFactory;
import sap.ass01.layered.UI.Mapper.Mapper;
import sap.ass01.layered.UI.Models.EBikeViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.List;

public class AdminView extends AbstractView {

    private final AdminService adminService = ServiceFactory.getAdminService();

    public AdminView(UserViewModel user) {
        super("Admin View", user);
        setupView();
        observeAllBikes();
        updateVisualizerPanel();
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
        List<EBikeViewModel> bikesModel = allBikes.stream()
                .map(Mapper::toDomain)
                .toList();
        eBikes = bikesModel;
        log("All bikes updated: " + allBikes);
        // Call a method to refresh the visual representation
        refreshView();
    }



    public void refreshView() {
        updateVisualizerPanel();
    }

    private void log(String msg){
        System.out.println("[AdminView] "+msg);
    }
}