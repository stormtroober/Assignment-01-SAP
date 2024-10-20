package sap.ass01.layered.domain.controller;

import sap.ass01.layered.domain.model.EBike;
import sap.ass01.layered.domain.model.Ride;
import sap.ass01.layered.domain.model.User;
import sap.ass01.layered.services.simulation.RideSimulation;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public interface DomainController {
    Collection<EBike> getBikes();
    void addBike(EBike bike);
    Optional<EBike> getBikeById(String bikeId);
    void updateBike(EBike bike);

    Collection<User> getUsers();
    void addUser(User user);
    Optional<User> getUserById(String userId);
    void updateUser(User user);

    Collection<Ride> getRide();
    void addRide(Ride ride);
    Optional<Ride> getRideById(String rideId);
    void updateRide(Ride ride);

}
