package sap.ass01.layered.services.simulation;


import sap.ass01.layered.services.dto.RideDTO;

public interface RideUpdateListener {
    void onRideUpdate(RideDTO rideDTO);
}