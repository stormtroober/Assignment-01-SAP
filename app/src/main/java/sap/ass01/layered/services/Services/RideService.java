package sap.ass01.layered.services.Services;

import sap.ass01.layered.services.simulation.RideUpdateListener;

import java.util.concurrent.CompletableFuture;

interface RideService {
    CompletableFuture<RideUpdateListener> startRide(String userId, String bikeId);
    CompletableFuture<Void> endRide(String userId, String bikeId);
}
