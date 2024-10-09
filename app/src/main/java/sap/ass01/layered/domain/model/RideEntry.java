package sap.ass01.layered.domain.model;

import sap.ass01.layered.services.simulation.RideSimulation;

public record RideEntry(Ride ride, RideSimulation simulation) {
}