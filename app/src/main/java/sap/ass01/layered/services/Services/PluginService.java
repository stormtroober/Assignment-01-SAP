package sap.ass01.layered.services.Services;

import sap.ass01.layered.domain.model.EBike;
import sap.ass01.layered.services.dto.EBikeDTO;
import sap.ass01.layered.services.dto.EBikeDTOExt;

import java.io.File;

public interface PluginService {
    void registerPlugin(String pluginID, File libFile);
    EBikeDTOExt applyPluginEffect(String pluginID, EBikeDTO bike);
}