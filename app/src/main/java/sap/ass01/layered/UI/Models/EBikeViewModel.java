package sap.ass01.layered.UI.Models;

import sap.ass01.layered.services.dto.EBikeDTO;

public class EBikeViewModel {
    private String id;
    private double x;
    private double y;
    private int batteryLevel;
    private String state;

    public EBikeViewModel(EBikeDTO eBikeDTO) {
        this.id = eBikeDTO.id();
        this.x = eBikeDTO.x();
        this.y = eBikeDTO.y();
        this.batteryLevel = eBikeDTO.batteryLevel();
        this.state = eBikeDTO.state();
    }

    public String getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public String getState() {
        return state;
    }
}