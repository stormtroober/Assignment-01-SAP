package sap.ass01.layered.services.impl;

import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import sap.ass01.layered.domain.controller.DomainController;
import sap.ass01.layered.domain.controller.DomainControllerImpl;
import sap.ass01.layered.domain.model.EBike;
import sap.ass01.layered.domain.model.Ride;
import sap.ass01.layered.domain.model.User;
import sap.ass01.layered.services.Services.AdminService;
import sap.ass01.layered.services.Services.LoginService;
import sap.ass01.layered.services.Services.UserService;
import sap.ass01.layered.services.dto.EBikeDTO;
import sap.ass01.layered.services.dto.RideDTO;
import sap.ass01.layered.services.dto.UserDTO;
import sap.ass01.layered.services.simulation.RideSimulation;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceImpl implements AdminService, LoginService, UserService {

    private DomainController domainController;
    // Subjects to emit bike updates
    private final BehaviorSubject<Collection<EBikeDTO>> allBikesSubject = BehaviorSubject.createDefault(Collections.emptyList());
    private final BehaviorSubject<Collection<EBikeDTO>> availableBikesSubject = BehaviorSubject.createDefault(Collections.emptyList());
    //Subject to emit user updates
    private final BehaviorSubject<Collection<UserDTO>> allUsersSubject = BehaviorSubject.createDefault(Collections.emptyList());

    // Map to keep track of ongoing rides and their simulations
    private final ConcurrentHashMap<String, RideSimulation> rideSimulations = new ConcurrentHashMap<>();

    public ServiceImpl() {
        domainController = new DomainControllerImpl();
        emitAllBikes();
        emitAvailableBikes();
        emitAllUsers();
    }

    // ------------------- AdminService Implementation -------------------

    @Override
    public Single<EBikeDTO> createEBike(String bikeId, double x, double y) {
        return Single.fromCallable(() -> {
            Optional<EBike> existingBike = domainController.getBikeById(bikeId);
            if (existingBike.isPresent()) {
                throw new IllegalArgumentException("Bike ID already exists.");
            }
            EBike bike = new EBike(bikeId, x, y, EBike.EBikeState.AVAILABLE,100 );
            domainController.addBike(bike);
            emitAllBikes();
            emitAvailableBikes();
            return mapToDTO(bike);
        }).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }

    @Override
    public Observable<Collection<EBikeDTO>> observeAllBikes() {
        return allBikesSubject.hide().observeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }

    @Override
    public Observable<Collection<UserDTO>> observeAllUsers() {
        return allUsersSubject.hide().observeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }

    @Override
    public Single<EBikeDTO> rechargeEBike(String bikeId) {
        return Single.fromCallable(() -> {
            EBike bike = domainController.getBikeById(bikeId)
                    .orElseThrow(() -> new IllegalArgumentException("Bike not found."));
            synchronized (Objects.requireNonNull(bike)) {
                bike.rechargeBattery();
            }
            domainController.updateBike(bike);
            emitAllBikes();
            emitAvailableBikes();
            return mapToDTO(bike);
        }).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }

    @Override
    public void refreshAllBikes() {
        emitAllBikes();
        emitAvailableBikes();
    }

    // ------------------- LoginService Implementation -------------------

    @Override
    public Completable signUp(String userId, boolean isAdmin) {
        return Completable.fromAction(() -> {
            if (domainController.getUserById(userId).isPresent()) {
                throw new IllegalArgumentException("User already exists.");
            }
            User user = new User(userId, isAdmin ? User.UserType.ADMIN : User.UserType.USER, 100);
            domainController.addUser(user);
            emitAllUsers();
        }).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }

    @Override
    public Single<UserDTO> logIn(String userId) {
        return Single.fromCallable(() -> {
            Optional<User> userOpt = domainController.getUserById(userId);
            if (userOpt.isEmpty()) {
                throw new IllegalArgumentException("User not found.");
            }
            User user = userOpt.get();
            return mapToDTO(user);

        }).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }

    // ------------------- UserService Implementation -------------------

    @Override
    public Observable<Collection<EBikeDTO>> observeAvailableBikes() {
        return availableBikesSubject.hide().observeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }

    @Override
    public Observable<RideDTO> startRide(String userId, String rideId, String bikeId) {
        return Observable.<RideDTO>create(emitter -> {
            // Validate user and bike
            //User user = users.get(userId);
            Optional<User> user = domainController.getUserById(userId);
            if (user.isEmpty()) {
                emitter.onError(new IllegalArgumentException("User not found."));
                return;
            }else if(user.get().getCredit() == 0){
                emitter.onError(new IllegalArgumentException("User has no credit."));
                return;
            }

            Optional<EBike> bike = domainController.getBikeById(bikeId);
            if (bike.isEmpty()) {
                emitter.onError(new IllegalArgumentException("Bike not found."));
                return;
            }else if (bike.get().getBatteryLevel() == 0){
                emitter.onError(new IllegalArgumentException("Bike has no battery."));
                return;
            }

            synchronized (bike.get()) {
                if (!bike.get().isAvailable()) {
                    emitter.onError(new IllegalStateException("Bike is not available."));
                    return;
                }
                bike.get().setState(EBike.EBikeState.IN_USE);
            }

            // Start ride
            Ride ride = new Ride(rideId, user.get(), bike.get());
            ride.start();
            //rideRepository.saveRide(Mapper.toDTO(ride));
            domainController.addRide(ride);
            RideSimulation simulation = new RideSimulation(ride, user.get());
            rideSimulations.put(ride.getId(), simulation);

            // Subscribe to ride updates from the RideSimulation
            simulation.getRideObservable()
                    .observeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                    .subscribe(
                            rideDTO -> {
                                // Emit every ride update to the subscriber
                                domainController.updateRide(ride);
                                domainController.updateBike(bike.get());
                                domainController.updateUser(user.get());
                                emitAllBikes();
                                emitAllUsers();

                                //emitter.onNext(rideDTO);
                            },
                            throwable -> {
                                // Emit error if something goes wrong
                                emitter.onError(throwable);
                            },
                            () -> {
                                // Complete the observable once the ride is done
                                handleRideCompletion(rideId);
                                domainController.updateRide(ride);
                                domainController.updateBike(bike.get());
                                domainController.updateUser(user.get());
                                emitter.onComplete();
                            }
                    );

            // Start the ride simulation process
            simulation.startSimulation();

            // Emit the current bike status immediately
            emitter.onNext(new RideDTO(
                    bike.get().getId(),
                    bike.get().getLocation().x(),
                    bike.get().getLocation().y(),
                    user.get().getCredit(),
                    bike.get().getBatteryLevel()
            ));

            // Emit all bikes' status
            emitAllBikes();
            emitAvailableBikes();
            emitAllUsers();
        }).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }


    @Override
    public Completable endRide(String userId, String rideId, String bikeId) {
        return Completable.create(emitter -> {
            Optional<Ride> ride = domainController.getRideById(rideId);
            if (ride.isEmpty()) {
                emitter.onError(new IllegalArgumentException("Ride not found."));
                return;
            }

            if (!ride.get().getUser().getId().equals(userId) ||
                    !ride.get().getEbike().getId().equals(bikeId)) {
                emitter.onError(new IllegalArgumentException("Ride does not match user and bike."));
                return;
            }

            // Stop simulation
            rideSimulations.get(rideId).stopSimulation();

            // Update bike state
            EBike bike = ride.get().getEbike();
            synchronized (bike) {
                bike.setState(EBike.EBikeState.AVAILABLE);
//                bike.rechargeBattery(); // Assuming ride completion recharges the bike
            }

            // End ride
           ride.get().end();

            // Update repositories
            domainController.updateRide(ride.get());
            domainController.updateBike(bike);
            domainController.updateUser(ride.get().getUser());

            emitAllBikes();
            emitAvailableBikes();
            emitAllUsers();

            emitter.onComplete();
        }).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }

    @Override
    public Observable<RideDTO> observeRide(String userId, String rideId) {
        Optional<Ride> ride = domainController.getRideById(rideId);
        if (ride.isEmpty()) {
            return Observable.error(new IllegalArgumentException("Ride not found."));
        }

        if (!ride.get().getUser().getId().equals(userId)) {
            return Observable.error(new IllegalArgumentException("Ride does not belong to the user."));
        }
        return rideSimulations.get(rideId).getRideObservable().hide().observeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());

    }

    @Override
    public Single<UserDTO> rechargeCredit(String userId, int amount) {
        return Single.fromCallable(() -> {
            Optional<User> user = domainController.getUserById(userId);
            if (user.isEmpty()) {
                throw new IllegalArgumentException("User not found.");
            }
            synchronized (Objects.requireNonNull(user.get())) {
                user.get().increaseCredit(amount);
            }
            domainController.updateUser(user.get());
            emitAllUsers();
            return mapToDTO(user.get());
        }).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }

    // ------------------- Helper Methods -------------------

    private void handleRideCompletion(String rideId) {

       Optional<Ride> ride = domainController.getRideById(rideId);
        if (ride.isPresent()) {
            rideSimulations.remove(rideId);
            emitAllBikes();
            emitAvailableBikes();
        }
    }

    private void emitAllBikes() {
        log("Emitting all bikes");
        Collection<EBikeDTO> bikeDTOs = new ArrayList<>();
        for (EBike bike : domainController.getBikes()) {
            bikeDTOs.add(mapToDTO(bike));
        }
        allBikesSubject.onNext(bikeDTOs);
    }

    private void emitAvailableBikes() {
        log("Emitting available bikes");
        Collection<EBikeDTO> availableDTOs = new ArrayList<>();
        for (EBike bike : domainController.getBikes()) {
            if (bike.isAvailable() ) {
                availableDTOs.add(mapToDTO(bike));
            }
        }
        availableBikesSubject.onNext(availableDTOs);
    }

    private void emitAllUsers() {
        log("Emitting all users");
        Collection<UserDTO> userDTOs = new ArrayList<>();
        for (User user : domainController.getUsers()) {
            userDTOs.add(mapToDTO(user));
        }
        allUsersSubject.onNext(userDTOs);
    }



    private EBikeDTO mapToDTO(EBike bike) {
        synchronized (bike) {
            return new EBikeDTO(
                    bike.getId(),
                    bike.getLocation().x(),
                    bike.getLocation().y(),
                    bike.getBatteryLevel(),
                    bike.getState().name()
            );
        }
    }

    private UserDTO mapToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getCredit(),
                user.getType().equals(User.UserType.ADMIN)
        );
    }

    private void log(String msg){
        System.out.println("[ServiceImpl] "+msg);
    }
}