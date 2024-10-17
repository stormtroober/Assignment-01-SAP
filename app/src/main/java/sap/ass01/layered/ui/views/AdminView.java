package sap.ass01.layered.ui.views;

import sap.ass01.layered.plugin.ColorStatePlugin;
import sap.ass01.layered.ui.models.EBikeViewModel;
import sap.ass01.layered.services.dto.UserDTO;
import sap.ass01.layered.services.Services.AdminService;
import sap.ass01.layered.services.dto.EBikeDTO;
import sap.ass01.layered.services.impl.ServiceFactory;
import sap.ass01.layered.ui.mapper.Mapper;
import sap.ass01.layered.ui.models.UserViewModel;
import sap.ass01.layered.ui.dialogs.admin.AddEBikeDialog;
import sap.ass01.layered.ui.dialogs.admin.RechargeBikeDialog;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class AdminView extends AbstractView {

    private final AdminService adminService = ServiceFactory.getAdminService();
    private final List<UserViewModel> userList = new ArrayList<>();

    public AdminView(UserViewModel user) {
        super("Admin View", user);
        setupView();
        observeAllBikes();
        observeAllUsers();
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
            RechargeBikeDialog rechargeBikeDialog = new RechargeBikeDialog(AdminView.this, adminService);
            rechargeBikeDialog.setVisible(true);
        });
        topPanel.add(rechargeBikeButton);

        JButton loadPluginButton = new JButton("Load Plugin");
        loadPluginButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String pluginID = selectedFile.getName().replace(".jar", "");
                pluginManager.registerPlugin(pluginID, selectedFile, ColorStatePlugin.class);
                log("Plugin loaded: " + pluginID);
                // Update all bikes and refresh the view
                adminService.observeAllBikes()
                        .subscribe(this::updateAllBikes);
            }
        });
        topPanel.add(loadPluginButton);
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

    private void observeAllUsers() {
        adminService.observeAllUsers()
                .subscribe(
                        this::updateAllUsers,
                        throwable -> {
                            // Handle error
                            System.err.println("Error observing available users: " + throwable.getMessage());
                        }
                );
    }

    private void updateAllUsers(Collection<UserDTO> userDTOS) {
        // Update the UI components with the new available users data
        List<UserViewModel> usersModel = userDTOS.stream()
                .map(Mapper::toDomain)
                .toList();
        userList.clear();
        userList.addAll(usersModel);
        log("All users updated: " + userDTOS);
        refreshView();
    }

    private void updateAllBikes(Collection<EBikeDTO> allBikes) {
        // Update the UI components with the new available bikes data
        // Apply plugin effect if loaded
        eBikes = allBikes.stream()
                .map(Mapper::toDomain)
                .map(bike -> applyEffect("ColorStateEffect", bike)) // Apply plugin effect if loaded
                .toList();
        log("All bikes updated: " + allBikes);
        // Call a method to refresh the visual representation
        refreshView();
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

    public EBikeViewModel applyEffect(String pluginID, EBikeViewModel bike) {
        ColorStatePlugin plugin = pluginManager.getPlugin(pluginID, ColorStatePlugin.class);
        if (plugin != null) {
            try {
                return plugin.colorState(bike);
            } catch (Exception e) {
                log("Error applying plugin: " + e.getMessage());
            }
        }
        return bike;
    }
}