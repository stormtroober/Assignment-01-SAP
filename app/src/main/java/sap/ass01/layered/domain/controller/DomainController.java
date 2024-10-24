package sap.ass01.layered.domain.controller;

import sap.ass01.layered.domain.model.EBike;
import sap.ass01.layered.domain.model.Ride;
import sap.ass01.layered.domain.model.User;

import java.util.Collection;
import java.util.Optional;

/**
 * Interface representing the domain controller for managing e-bikes, users, and rides.
 */
public interface DomainController {

    /**
     * Retrieves all e-bikes.
     *
     * @return a collection of all e-bikes
     */
    Collection<EBike> getBikes();

    /**
     * Adds a new e-bike.
     *
     * @param bike the e-bike to be added
     */
    void addBike(EBike bike);

    /**
     * Finds an e-bike by its ID.
     *
     * @param bikeId the ID of the e-bike to be found
     * @return an Optional containing the found e-bike, or empty if not found
     */
    Optional<EBike> getBikeById(String bikeId);

    /**
     * Updates the given e-bike.
     *
     * @param bike the e-bike to be updated
     */
    void updateBike(EBike bike);

    /**
     * Retrieves all users.
     *
     * @return a collection of all users
     */
    Collection<User> getUsers();

    /**
     * Adds a new user.
     *
     * @param user the user to be added
     */
    void addUser(User user);

    /**
     * Finds a user by their ID.
     *
     * @param userId the ID of the user to be found
     * @return an Optional containing the found user, or empty if not found
     */
    Optional<User> getUserById(String userId);

    /**
     * Updates the given user.
     *
     * @param user the user to be updated
     */
    void updateUser(User user);

    /**
     * Retrieves all rides.
     *
     * @return a collection of all rides
     */
    Collection<Ride> getRide();

    /**
     * Adds a new ride.
     *
     * @param ride the ride to be added
     */
    void addRide(Ride ride);

    /**
     * Finds a ride by its ID.
     *
     * @param rideId the ID of the ride to be found
     * @return an Optional containing the found ride, or empty if not found
     */
    Optional<Ride> getRideById(String rideId);

    /**
     * Updates the given ride.
     *
     * @param ride the ride to be updated
     */
    void updateRide(Ride ride);
}