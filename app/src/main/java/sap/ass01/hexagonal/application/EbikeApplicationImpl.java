package sap.ass01.hexagonal.application;

import io.reactivex.rxjava3.core.Observable;
import sap.ass01.hexagonal.application.entities.EBikeDTO;
import sap.ass01.hexagonal.application.entities.EBikeState;
import sap.ass01.hexagonal.application.entities.RideDTO;
import sap.ass01.hexagonal.application.entities.UserDTO;
import sap.ass01.hexagonal.domain.model.EBike;
import sap.ass01.hexagonal.domain.model.Ride;
import sap.ass01.hexagonal.domain.model.RideSimulation;
import sap.ass01.hexagonal.domain.model.User;
import sap.ass01.hexagonal.domain.mapper.Mapper;


import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class EbikeApplicationImpl implements EbikeApplication{

    private final ConcurrentHashMap<String, EBike> bikes = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Ride, RideSimulation> rides = new ConcurrentHashMap<>();

    @Override
    public Collection<EBikeDTO> getBikes() {
        return bikes.values().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public Optional<EBikeDTO> addEbike(String id, double x, double y) {
        if (bikes.containsKey(id)) {
            return Optional.empty();
        }
        EBike bike = new EBike(id, x, y, EBikeState.AVAILABLE, 100);
        bikes.put(id,bike);
        return Optional.of(Mapper.toDTO(bike));
    }


    @Override
    public Optional<EBikeDTO> getEbike(String id) {
        return Optional.ofNullable(bikes.get(id)).map(Mapper::toDTO);
    }

    @Override
    public Optional<EBikeDTO> rechargeBike(String id) {
        EBike existingBike = bikes.get(id);
        if (existingBike != null) {
            existingBike.rechargeBattery();
            return Optional.of(Mapper.toDTO(existingBike));
        }
        return Optional.empty();
    }


    @Override
    public Collection<UserDTO> getUsers() {
        return users.values().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public boolean addUser(String userId, boolean isAdmin) {
        if (users.containsKey(userId)) {
            return false;
        }
        User user = new User(userId, isAdmin? User.UserType.ADMIN : User.UserType.USER, 100);
        users.put(userId, user);
        return true;
    }


    @Override
    public Optional<UserDTO> getUser(String userId) {
        return Optional.ofNullable(users.get(userId)).map(Mapper::toDTO);
    }

    @Override
    public Optional<UserDTO> rechargeCredit(String id, int credit) {
        User existingUser = users.get(id);
        if (existingUser != null) {
            existingUser.increaseCredit(credit);
            return Optional.of(Mapper.toDTO(existingUser));
        }
        return Optional.empty();
    }


    @Override
    public Observable<RideDTO> startRide(String rideId, String userId, String bikeId) {
        return Observable.<RideDTO>create(emitter -> {
            Optional<User> userOptional = Optional.ofNullable(users.get(userId));
            Optional<EBike> bikeOptional = Optional.ofNullable(bikes.get(bikeId));
            if (userOptional.isEmpty()) {
                emitter.onError(new IllegalArgumentException("User not found"));
            } else if (bikeOptional.isEmpty()) {
                emitter.onError(new IllegalArgumentException("Bike not found"));
            } else {
                User user = userOptional.get();
                EBike bike = bikeOptional.get();
                if (user.getCredit() == 0) {
                    emitter.onError(new IllegalArgumentException("Insufficient credit"));
                } else if (bike.getBatteryLevel() == 0) {
                    emitter.onError(new IllegalArgumentException("Bike has no battery"));
                } else if (!bike.isAvailable()){
                    emitter.onError(new IllegalArgumentException("Bike is not available"));
                } else {
                    Ride ride = new Ride(rideId, user, bike);
                    RideSimulation rideSimulation = new RideSimulation(ride, user);
                    ride.start();
                    rides.put(ride, rideSimulation);
                    rideSimulation.getRideObservable()
                            .observeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                            .subscribe(
                                    rideVal -> {


                                        emitter.onNext(Mapper.toDTO(rideVal));
                                    },
                                    throwable -> {
                                        // Emit error if something goes wrong
                                        emitter.onError(throwable);
                                    },
                                    () -> {
                                        // Complete the observable once the ride is done
                                        handleRideCompletion(rideId);
                                        emitter.onComplete();
                                    }
                            );

                    // Start the ride simulation process
                    rideSimulation.startSimulation();

                    // Emit the current bike status immediately
                    emitter.onNext(Mapper.toDTO(ride));


                }
            }

        }).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }

    private void handleRideCompletion(String rideId) {
        Ride ride = rides.keySet().stream().filter(r -> r.getId().equals(rideId)).findFirst().orElse(null);
        if (ride != null) {
            rides.remove(ride);
        }
    }

    @Override
    public void endRide(RideDTO ride) {

    }

    @Override
    public Optional<RideDTO> getRide(String rideId) {
        return rides.keySet().stream().filter(r -> r.getId().equals(rideId)).findFirst().map(Mapper::toDTO);
    }

    @Override
    public Observable<RideDTO> getRideSimulationObservable(String id) {
        return rides.keySet().stream().filter(r -> r.getId().equals(id)).findFirst().map(r -> rides.get(r).getRideObservable().map(Mapper::toDTO).hide().observeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())).orElse(null);
    }
}
