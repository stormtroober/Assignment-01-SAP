package sap.ass01.hexagonal.application;

import io.reactivex.rxjava3.core.Observable;
import sap.ass01.hexagonal.application.entities.EBikeDTO;
import sap.ass01.hexagonal.application.entities.RideDTO;
import sap.ass01.hexagonal.application.entities.UserDTO;

import java.util.Collection;
import java.util.Optional;

public interface EbikeApplication {
    Collection<EBikeDTO> getBikes();
    void addEbike(EBikeDTO ebikeDTO);
    Optional<EBikeDTO> getEbike(String id);
    void rechargeBike(String id);

    Collection<UserDTO> getUsers();
    void addUser(UserDTO user);
    Optional<UserDTO> getUser(String userId);
    void rechargeCredit(String id, int credit);

    Observable<RideDTO> startRide(String rideId, String userId, String bikeId);
    void endRide(RideDTO ride);
    Optional<RideDTO> getRide(String rideId);
    Observable<RideDTO> getRideSimulationObservable(String id);

}
