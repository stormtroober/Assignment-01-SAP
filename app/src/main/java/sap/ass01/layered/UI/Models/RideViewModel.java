package sap.ass01.layered.UI.Models;

import sap.ass01.layered.services.dto.RideDTO;

public class RideViewModel {
    private String id;
    private double x;
    private double y;
    private int credit;
    private int charge;

    public RideViewModel(RideDTO rideDTO) {
        this.id = rideDTO.id();
        this.x = rideDTO.x();
        this.y = rideDTO.y();
        this.credit = rideDTO.credit();
        this.charge = rideDTO.charge();
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

    public int getCredit() {
        return credit;
    }

    public int getCharge() {
        return charge;
    }
}