package sap.ass01.hexagonal.application.ports;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import sap.ass01.hexagonal.application.ports.entities.EBikeDTO;
import sap.ass01.hexagonal.application.ports.entities.RideDTO;
import sap.ass01.hexagonal.application.ports.entities.UserDTO;


import java.util.Collection;

public interface UserViewPort {
    /**
     * Observes available E-Bikes in real-time.
     * Users can subscribe to receive updates about available bikes.
     * @return Observable emitting collections of EBikeDTO.
     */
    Observable<Collection<EBikeDTO>> observeAvailableBikes();

    /**
     * Starts a ride for a user on a specific bike.
     *
     * @param userId ID of the user.
     * @param rideId Unique ID for the ride.
     * @param bikeId ID of the bike.
     * @return Completable indicating success or failure.
     */
    Observable<RideDTO> startRide(String userId, String rideId, String bikeId);

    /**
     * Ends a ride for a user on a specific bike.
     * @param userId ID of the user.
     * @param rideId ID of the ride.
     * @param bikeId ID of the bike.
     * @return Completable indicating success or failure.
     */
    Completable endRide(String userId, String rideId, String bikeId);

    /**
     * Observes ride updates specific to a user's ride.
     * @param userId ID of the user.
     * @param rideId ID of the ride.
     * @return Observable emitting RideDTO updates.
     */
    Observable<RideDTO> observeRide(String userId, String rideId);

    /**
     * Recharges the credit of a user.
     * @param userId ID of the user.
     * @param amount Amount to recharge.
     * @return Single emitting the updated UserDTO or an error.
     */
    Single<UserDTO> rechargeCredit(String userId, int amount);
}