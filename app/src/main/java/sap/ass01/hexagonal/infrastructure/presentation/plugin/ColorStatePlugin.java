package sap.ass01.hexagonal.infrastructure.presentation.plugin;

import sap.ass01.hexagonal.application.ports.entities.EBikeDTO;

import java.awt.*;

public interface ColorStatePlugin {
    Color colorState(EBikeDTO bike);
}