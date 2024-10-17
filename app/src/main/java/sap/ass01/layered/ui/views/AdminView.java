// File: AdminView.java
package sap.ass01.layered.ui.views;

import sap.ass01.layered.plugin.ColorStatePlugin;
import sap.ass01.layered.plugin.PluginClassLoader;
import sap.ass01.layered.ui.dialogs.admin.AddEBikeDialog;
import sap.ass01.layered.ui.dialogs.admin.RechargeBikeDialog;
import sap.ass01.layered.ui.models.UserViewModel;
import sap.ass01.layered.services.Services.AdminService;
import sap.ass01.layered.services.dto.EBikeDTO;
import sap.ass01.layered.services.impl.ServiceFactory;
import sap.ass01.layered.ui.mapper.Mapper;
import sap.ass01.layered.ui.models.EBikeViewModel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Collection;
import java.util.List;

public class AdminView extends AbstractView {

    private final AdminService adminService = ServiceFactory.getAdminService();
    private ColorStatePlugin plugin;

    public AdminView(UserViewModel user) {
        super("Admin View", user);
        setupView();
        observeAllBikes();
        updateVisualizerPanel();
        //registerNewEffectPlugin("ColorStateEffect", new File("plugins/ColorStateEffect.jar"));
        //testPluginOnBikeInstance();
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

    private void registerNewEffectPlugin(String pluginID, File libFile)  {
        try{
            var loader = new PluginClassLoader(libFile.getAbsolutePath());
            String className = "sap.ass01.layered.effects.ColorStateEffect";
            Class<?> pluginClass = loader.loadClass(className);
            plugin = (ColorStatePlugin) pluginClass.getDeclaredConstructor(null).newInstance(null);
            log("Added plugin-in " + pluginID);
        } catch (Exception e) {
            log("Error loading plugin: " + e.getMessage());
        }
    }

    public EBikeViewModel applyEffect(EBikeViewModel bike) {
        EBikeViewModel updatedBike = bike;
        if (plugin != null) {
            try{
                updatedBike = plugin.colorState(bike);
            } catch (Exception e) {
                log("Error applying plugin: " + e.getMessage());
            }
        }
        return updatedBike;
    }

    private void testPluginOnBikeInstance() {
        EBikeViewModel bike = new EBikeViewModel("bike1", 10.0, 20.0, 80, "active");
        log("Before applying plugin: " + bike);
        log("After applying plugin: " + applyEffect(bike));
    }
}