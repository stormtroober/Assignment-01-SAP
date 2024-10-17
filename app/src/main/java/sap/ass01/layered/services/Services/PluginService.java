package sap.ass01.layered.services;

import sap.ass01.layered.plugin.ColorStatePlugin;

import java.io.File;

public interface PluginService {
    void registerPlugin(String pluginID, File libFile);
    ColorStatePlugin getColorStatePlugin(String pluginID);
}