package sap.ass01.hexagonal.infrastructure.presentation.plugin;

import sap.ass01.hexagonal.application.ports.entities.EBikeDTO;

import java.awt.*;
import java.io.File;

public class PluginServiceImpl implements PluginService {
    private final PluginManager pluginManager = new PluginManager();

    @Override
    public void registerPlugin(String pluginID, File libFile) {
        pluginManager.registerPlugin(pluginID, libFile, ColorStatePlugin.class);
    }

    @Override
    public EBikeDTOExt applyPluginEffect(String pluginID, EBikeDTO bike) {
        ColorStatePlugin plugin = pluginManager.getPlugin(pluginID, ColorStatePlugin.class);
        Color color = Color.BLACK;
        if (plugin != null) {
            color = plugin.colorState(bike);
        }
        return new EBikeDTOExt(bike.id(), bike.x(), bike.y(), bike.battery(), bike.state(), color);
    }
}
