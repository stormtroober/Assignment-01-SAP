package sap.ass01.hexagonal.application.ports;

import io.reactivex.rxjava3.core.Observable;
import sap.ass01.hexagonal.application.ports.entities.EBikeDTO;
import sap.ass01.hexagonal.application.ports.entities.RideDTO;
import sap.ass01.hexagonal.application.ports.entities.UserDTO;

import java.util.Collection;
import java.util.Optional;

public interface EBikeApplication {
/**
 * Retrieves all e-bikes.
 *
 * @return a collection of all e-bikes.
 */
Collection<EBikeDTO> getBikes();

/**
 * Adds a new e-bike.
 *
 * @param id the ID of the e-bike.
 * @param x the x-coordinate of the e-bike's location.
 * @param y the y-coordinate of the e-bike's location.
 * @return an Optional containing the added e-bike if successful, or an empty Optional if the e-bike ID already exists.
 */
Optional<EBikeDTO> addEbike(String id, double x, double y);

/**
 * Retrieves an e-bike by its ID.
 *
 * @param id the ID of the e-bike.
 * @return an Optional containing the e-bike if found, or an empty Optional if the e-bike ID does not exist.
 */
Optional<EBikeDTO> getEbike(String id);

/**
 * Recharges an e-bike by its ID.
 *
 * @param id the ID of the e-bike.
 * @return an Optional containing the recharged e-bike if successful, or an empty Optional if the e-bike ID does not exist.
 */
Optional<EBikeDTO> rechargeBike(String id);

/**
 * Retrieves all users.
 *
 * @return a collection of all users.
 */
Collection<UserDTO> getUsers();

/**
 * Adds a new user.
 *
 * @param userId the ID of the user.
 * @param isAdmin whether the user is an admin.
 * @return true if the user was added successfully, false if the user ID already exists.
 */
boolean addUser(String userId, boolean isAdmin);

/**
 * Retrieves a user by their ID.
 *
 * @param userId the ID of the user.
 * @return an Optional containing the user if found, or an empty Optional if the user ID does not exist.
 */
Optional<UserDTO> getUser(String userId);

/**
 * Recharges a user's credit.
 *
 * @param id the ID of the user.
 * @param credit the amount of credit to add.
 * @return an Optional containing the user with updated credit if successful, or an empty Optional if the user ID does not exist.
 */
Optional<UserDTO> rechargeCredit(String id, int credit);

/**
 * Starts a ride.
 *
 * @param rideId the ID of the ride.
 * @param userId the ID of the user.
 * @param bikeId the ID of the e-bike.
 * @return an Observable emitting the ride updates.
 */
Observable<RideDTO> startRide(String rideId, String userId, String bikeId);

/**
 * Ends a ride.
 *
 * @param ride the ride to end.
 * @return true if the ride was ended successfully, false otherwise.
 */
boolean endRide(RideDTO ride);

/**
 * Retrieves a ride by its ID.
 *
 * @param rideId the ID of the ride.
 * @return an Optional containing the ride if found, or an empty Optional if the ride ID does not exist.
 */
Optional<RideDTO> getRide(String rideId);

/**
 * Retrieves an observable for ride simulation updates.
 *
 * @param id the ID of the ride.
 * @return an Observable emitting the ride simulation updates.
 */
Observable<RideDTO> getRideSimulationObservable(String id);

}
