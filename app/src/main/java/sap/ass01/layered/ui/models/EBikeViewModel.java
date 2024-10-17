// File: EBikeViewModel.java
package sap.ass01.layered.ui.models;

import java.awt.*;

public record EBikeViewModel(String id, double x, double y, int batteryLevel, String state, Color color) {
    private static final Color DEFAULT_COLOR = Color.BLACK; // Default color

    public EBikeViewModel(String id, double x, double y, int batteryLevel, String state) {
        this(id, x, y, batteryLevel, state, DEFAULT_COLOR);
    }

    public EBikeViewModel updateBatteryLevel(int batteryLevel) {
        return new EBikeViewModel(id, x, y, batteryLevel, state, color);
    }

    public EBikeViewModel updateState(String state) {
        return new EBikeViewModel(id, x, y, batteryLevel, state, color);
    }

    public EBikeViewModel updateColor(Color color) {
        return new EBikeViewModel(id, x, y, batteryLevel, state, color);
    }

    public EBikeViewModel updateLocation(double newX, double newY) {
        return new EBikeViewModel(id, newX, newY, batteryLevel, state, color);
    }
}