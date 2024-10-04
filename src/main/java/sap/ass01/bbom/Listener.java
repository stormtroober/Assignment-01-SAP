package sap.ass01.bbom;

public interface Listener {
    void notifyModelChanged();

    void notifyRideStart(Ride ride);
}