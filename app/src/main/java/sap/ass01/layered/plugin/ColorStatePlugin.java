package sap.ass01.layered.plugin;

import sap.ass01.layered.domain.model.EBike;
import sap.ass01.layered.services.dto.EBikeDTO;
import sap.ass01.layered.ui.models.EBikeViewModel;

import java.awt.*;

public interface ColorStatePlugin {
    Color colorState(EBikeDTO bike);
}