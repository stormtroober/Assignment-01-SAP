package sap.ass01.layered.presentation.views;

import sap.ass01.layered.presentation.dialogs.admin.AddEBikeDialog;
import sap.ass01.layered.presentation.dialogs.admin.RechargeBikeDialog;
import sap.ass01.layered.presentation.mapper.Mapper;
import sap.ass01.layered.presentation.models.EBikeViewModel;
import sap.ass01.layered.presentation.models.UserViewModel;
import sap.ass01.layered.presentation.plugin.EBikeDTOExt;
import sap.ass01.layered.presentation.plugin.PluginService;
import sap.ass01.layered.presentation.plugin.PluginServiceImpl;
import sap.ass01.layered.presentation.PresentationController;
import sap.ass01.layered.services.dto.EBikeDTO;

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
        observeAllUsers();
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
                    System.err.println("Error observing available bikes: " + throwable.getMessage());
                });
    }

    private void observeAllUsers() {
        presentationController.observeAllUsers(
                this::updateAllUsers,
                throwable -> {
                    System.err.println("Error observing available users: " + throwable.getMessage());
                }
        );
    }

    private void updateAllUsers(List<UserViewModel> userModels) {
        userList.clear();
        userList.addAll(userModels);
        log("All users updated");
        refreshView();
    }

    private void updateAllBikes(Collection<EBikeDTO> allBikes) {
        eBikes = allBikes.stream()
                .map(bike -> {
                    EBikeDTOExt bikeExt = pluginService.applyPluginEffect("ColorStateEffect", bike);
                    return Mapper.toDomain(bikeExt);
                })
                .toList();
        log("All bikes updated: " + allBikes);
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
}