package sap.ass01.frontend.views;

import sap.ass01.frontend.models.EBikeViewModel;
import sap.ass01.layered.presentation.PresentationController;
import sap.ass01.layered.services.Services.AdminService;
import sap.ass01.layered.services.dto.EBikeDTO;
import sap.ass01.layered.services.dto.UserDTO;
import sap.ass01.layered.services.impl.ServiceFactory;
import sap.ass01.frontend.dialogs.admin.AddEBikeDialog;
import sap.ass01.frontend.dialogs.admin.RechargeBikeDialog;
import sap.ass01.layered.presentation.mapper.Mapper;
import sap.ass01.frontend.models.UserViewModel;
import sap.ass01.frontend.plugin.EBikeDTOExt;
import sap.ass01.frontend.plugin.PluginService;
import sap.ass01.frontend.plugin.PluginServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdminView extends AbstractView {

    private final PresentationController presentationController = new PresentationController();
    private final PluginService pluginService =  new PluginServiceImpl();
    private final List<UserViewModel> userList = new ArrayList<>();

    public AdminView(UserViewModel user) {
        super("Admin View", user);
        setupView();
        observeAllBikes();
        //updateAllBikes();
        observeAllUsers();
        //updateAllUsers();
        refreshView();
    }

    private void setupView() {
        topPanel.setLayout(new FlowLayout());

        JButton addBikeButton = new JButton("Add Bike");
        addBikeButton.addActionListener(e -> {
            AddEBikeDialog addEBikeDialog = new AddEBikeDialog(AdminView.this, presentationController);
            addEBikeDialog.setVisible(true);
        });
        topPanel.add(addBikeButton);

        JButton rechargeBikeButton = new JButton("Recharge Bike");
        rechargeBikeButton.addActionListener(e -> {
            RechargeBikeDialog rechargeBikeDialog = new RechargeBikeDialog(AdminView.this, presentationController);
            rechargeBikeDialog.setVisible(true);
        });
        topPanel.add(rechargeBikeButton);

        JButton loadPluginButton = getLoadPluginButton();
        topPanel.add(loadPluginButton);
    }

    private JButton getLoadPluginButton() {
        JButton loadPluginButton = new JButton("Load Plugin");
        loadPluginButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String pluginID = selectedFile.getName().replace(".jar", "");
                pluginService.registerPlugin(pluginID, selectedFile);
                presentationController.refreshAllBikes();
            }
        });
        return loadPluginButton;
    }

    private void observeAllBikes() {
        presentationController.observeAllBikes(
                this::updateAllBikes,
                throwable -> {
                    // Handle error
                    System.err.println("Error observing available bikes: " + throwable.getMessage());
                });
                /*.subscribe(
                        this::updateAllBikes,
                        throwable -> {
                            // Handle error
                            System.err.println("Error observing available bikes: " + throwable.getMessage());
                        }
                );*/
    }

    private void observeAllUsers() {
        presentationController.observeAllUsers(
                this::updateAllUsers,
                throwable -> {
                    // Handle error
                    System.err.println("Error observing available users: " + throwable.getMessage());
                }
        );
                /*.subscribe(
                        this::updateAllUsers,
                        throwable -> {
                            // Handle error
                            System.err.println("Error observing available users: " + throwable.getMessage());
                        }
                );*/
    }

    /*private void updateAllUsers(Collection<UserDTO> userDTOS) {
        // Update the UI components with the new available users data
        List<UserViewModel> usersModel = userDTOS.stream()
                .map(Mapper::toDomain)
                .toList();
        userList.clear();
        userList.addAll(usersModel);
        log("All users updated: " + userDTOS);
        refreshView();
    }*/



    /*private void updateAllBikes(Collection<EBikeDTO> allBikes) {
    // Update the UI components with the new available bikes data
    eBikes = allBikes.stream()
            .map(bike -> {
                // Convert EBikeDTO to EBikeDTOExt using the plugin
                EBikeDTOExt bikeExt = pluginService.applyPluginEffect("ColorStateEffect", bike);
                // Map EBikeDTOExt to EBikeViewModel
                return Mapper.toDomain(bikeExt);
            })
            .toList();

    log("All bikes updated: " + allBikes);
    // Call a method to refresh the visual representation
    refreshView();
}*/
    private void updateAllUsers(List<UserViewModel> userModels) {
        userList.clear();
        userList.addAll(userModels);
        log("All users updated");
        refreshView();
    }

    private void updateAllBikes(List<EBikeViewModel> bikeModels) {
        eBikes = bikeModels; // Update the list of bikes
        log("All bikes updated");
        refreshView(); // Refresh the visual representation
    }

    @Override
    protected void paintAdminView(Graphics2D g2) {
        super.paintAdminView(g2);
        printAllUsers(g2);
    }

    private void printAllUsers(Graphics2D g2) {
        int dy = 20;
        g2.drawString("ALL USERS: ", 10, dy);
        dy += 15;
        for (UserViewModel user : userList) {
            g2.drawString("User ID: " + user.id() + " - Credit: " + user.credit(), 10, dy);
            dy += 15;
        }
    }

    public void refreshView() {
        updateVisualizerPanel();
    }

    private void log(String msg) {
        System.out.println("[AdminView] " + msg);
    }
}