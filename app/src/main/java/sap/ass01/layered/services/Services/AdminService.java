package sap.ass01.layered.services.Services;

import sap.ass01.layered.services.dto.EBikeDTO;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import sap.ass01.layered.services.dto.UserDTO;

import java.util.Collection;

public interface AdminService {
    /**
     * Creates a new E-Bike with the given ID and initial location.
     * @param bikeId Unique identifier for the bike.
     * @param x Initial X-coordinate.
     * @param y Initial Y-coordinate.
     * @return Single emitting the created EBikeDTO or an error.
     */
    Single<EBikeDTO> createEBike(String bikeId, double x, double y);

    /**
     * Observes all E-Bikes in real-time.
     * Admins can subscribe to receive updates about all bikes.
     * @return Observable emitting collections of EBikeDTO.
     */
    Observable<Collection<EBikeDTO>> observeAllBikes();

    /**
     * Observes all users in real-time.
     * Admins can subscribe to receive updates about all users.
     * @return Observable emitting collections of UserDTO.
     */
    Observable<Collection<UserDTO>> observeAllUsers();

    /**
     * Recharges the battery of the E-Bike with the given ID.
     * @param bikeId Unique identifier for the bike.
     * @return Single emitting the updated EBikeDTO or an error.
     */
    Single<EBikeDTO> rechargeEBike(String bikeId);


}