package sap.ass01.hexagonal.application.ports;

import sap.ass01.hexagonal.application.ports.entities.RideDTO;

import java.util.List;
import java.util.Optional;

public interface RideRepository {
/**
 * Finds a ride by its ID.
 *
 * @param rideId the ID of the ride to find.
 * @return an Optional containing the found ride, or an empty Optional if no ride is found.
 */
Optional<RideDTO> findRideById(String rideId);

/**
 * Finds all rides.
 *
 * @return a list of all rides.
 */
List<RideDTO> findAllRides();

/**
 * Saves a new ride.
 *
 * @param ride the ride to save.
 */
void saveRide(RideDTO ride);

/**
 * Updates an existing ride.
 *
 * @param ride the ride to update.
 */
void updateRide(RideDTO ride);

/**
 * Cleans the repository, removing all rides.
 */
void clean();
}
