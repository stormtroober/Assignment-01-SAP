package sap.ass01.layered.persistence.repository;

import sap.ass01.layered.persistence.dto.RideDTO;

import java.util.List;
import java.util.Optional;

/**
 * Interface representing the repository for managing rides.
 */
public interface RideRepository {

    /**
     * Finds a ride by its ID.
     *
     * @param rideId the ID of the ride to be found
     * @return an Optional containing the found ride, or empty if not found
     */
    Optional<RideDTO> findRideById(String rideId);

    /**
     * Retrieves all rides.
     *
     * @return a list of all rides
     */
    List<RideDTO> findAllRides();

    /**
     * Saves the given ride to the repository.
     *
     * @param ride the ride to be saved
     */
    void saveRide(RideDTO ride);

    /**
     * Updates the given ride in the repository.
     *
     * @param ride the ride to be updated
     */
    void updateRide(RideDTO ride);

    /**
     * Cleans the database, removing all rides.
     */
    void cleanDatabase();
}