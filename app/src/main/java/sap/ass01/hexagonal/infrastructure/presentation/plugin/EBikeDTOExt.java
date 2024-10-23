package sap.ass01.hexagonal.infrastructure.presentation.plugin;

import java.awt.*;

public record EBikeDTOExt(String id, double x, double y, int batteryLevel, String state, Color color) {
}