package sap.ass01.hexagonal.infrastructure.presentation.plugin;

import sap.ass01.hexagonal.application.ports.entities.EBikeDTO;

import java.io.File;

public interface PluginService {
    void registerPlugin(String pluginID, File libFile);
    EBikeDTOExt applyPluginEffect(String pluginID, EBikeDTO bike);
}