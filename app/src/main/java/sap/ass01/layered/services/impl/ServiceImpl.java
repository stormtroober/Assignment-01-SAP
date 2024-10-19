package sap.ass01.layered.services.impl;

import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import sap.ass01.layered.domain.mapper.Mapper;
import sap.ass01.layered.domain.model.EBike;
import sap.ass01.layered.domain.model.Ride;
import sap.ass01.layered.domain.model.RideEntry;
import sap.ass01.layered.domain.model.User;
import sap.ass01.layered.persistence.repository.DatabaseType;
import sap.ass01.layered.persistence.repository.EBikeRepository;
import sap.ass01.layered.persistence.repository.RideRepository;
import sap.ass01.layered.persistence.repository.UserRepository;
import sap.ass01.layered.persistence.repository.factory.EBikeRepositoryFactory;
import sap.ass01.layered.persistence.repository.factory.RideRepositoryFactory;
import sap.ass01.layered.persistence.repository.factory.UserRepositoryFactory;
import sap.ass01.layered.plugin.ColorStatePlugin;
import sap.ass01.layered.domain.model.EBikeExtended;
import sap.ass01.layered.plugin.PluginManager;
import sap.ass01.layered.services.Services.PluginService;
import sap.ass01.layered.services.Services.AdminService;
import sap.ass01.layered.services.Services.LoginService;
import sap.ass01.layered.services.Services.UserService;
import sap.ass01.layered.services.dto.EBikeDTO;
import sap.ass01.layered.services.dto.EBikeDTOExt;
import sap.ass01.layered.services.dto.RideDTO;
import sap.ass01.layered.services.dto.UserDTO;
import sap.ass01.layered.services.simulation.RideSimulation;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceImpl implements AdminService, LoginService, UserService, PluginService {
    private final PluginManager pluginManager = new PluginManager();

    private final ConcurrentHashMap<String, EBike> bikes = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, RideEntry> rideEntries = new ConcurrentHashMap<>();

    // Subjects to emit bike updates
    private final BehaviorSubject<Collection<EBikeDTO>> allBikesSubject = BehaviorSubject.createDefault(Collections.emptyList());
    private final BehaviorSubject<Collection<EBikeDTO>> availableBikesSubject = BehaviorSubject.createDefault(Collections.emptyList());
    //Subject to emit user updates
    private final BehaviorSubject<Collection<UserDTO>> allUsersSubject = BehaviorSubject.createDefault(Collections.emptyList());

    private final EBikeRepository eBikeRepository;
    private final UserRepository userRepository;
    private final RideRepository rideRepository;

    public ServiceImpl() {
        eBikeRepository = EBikeRepositoryFactory.createRepository(DatabaseType.IN_MEMORY);
        userRepository = UserRepositoryFactory.createRepository(DatabaseType.IN_MEMORY);
        rideRepository = RideRepositoryFactory.createRepository(DatabaseType.IN_MEMORY);

        // Load data from repositories
        eBikeRepository.findAllEBikes().forEach(eBike -> {
            bikes.put(eBike.id(), Mapper.toDomain(eBike));
        });

        userRepository.findAllUsers().forEach(user -> {
            users.put(user.id(), Mapper.toDomain(user));
        });

        rideRepository.findAllRides().forEach(ride -> {
            User user = Mapper.toDomain(ride.user());
            EBike bike = Mapper.toDomain(ride.bike());
            if (bikes.contains(bike) && users.contains(user)) {
                Ride rideTmp = Mapper.toDomain(ride, user, bike);
                rideEntries.put(ride.id(), new RideEntry(rideTmp, new RideSimulation(rideTmp, user)));
            }
        });

        emitAllBikes();
        emitAvailableBikes();
        emitAllUsers();
    }

    // ------------------- PluginService Implementation -------------------


    @Override
    public void registerPlugin(String pluginID, File libFile) {
        pluginManager.registerPlugin(pluginID, libFile, ColorStatePlugin.class);
        emitAllBikes();
    }

    @Override
    public EBikeDTOExt applyPluginEffect(String pluginID, EBikeDTO bike) {
        ColorStatePlugin plugin = pluginManager.getPlugin(pluginID, ColorStatePlugin.class);
        Color color = Color.BLACK;
        if (plugin != null) {
            color = plugin.colorState(bike);
        }
        return new EBikeDTOExt(bike.id(), bike.x(), bike.y(), bike.batteryLevel(), bike.state(), color);
    }

    // ------------------- AdminService Implementation -------------------

    @Override
    public Single<EBikeDTO> createEBike(String bikeId, double x, double y) {
        return Single.fromCallable(() -> {
            Optional<sap.ass01.layered.persistence.dto.EBikeDTO> existingBike = eBikeRepository.findEBikeById(bikeId);
            if (existingBike.isPresent()) {
                throw new IllegalArgumentException("Bike ID already exists.");
            }
            EBike bike = new EBike(bikeId, x, y, EBike.EBikeState.AVAILABLE,100 );
            eBikeRepository.saveEBike(Mapper.toDTO(bike));
            bikes.put(bikeId, bike);
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
            EBike bike = bikes.get(bikeId);
            if (bike == null) {
                eBikeRepository.findEBikeById(bikeId)
                        .ifPresentOrElse(eBikeDTO -> bikes.put(eBikeDTO.id(), Mapper.toDomain(eBikeDTO)),
                                () -> {
                                    throw new IllegalArgumentException("Bike not found.");
                                });
            }
            synchronized (Objects.requireNonNull(bike)) {
                bike.rechargeBattery();
            }
            eBikeRepository.updateEBike(Mapper.toDTO(bike));
            emitAllBikes();
            emitAvailableBikes();
            return mapToDTO(bike);
        }).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }

    // ------------------- LoginService Implementation -------------------

    @Override
    public Completable signUp(String userId, boolean isAdmin) {
        return Completable.fromAction(() -> {
            if (users.containsKey(userId)) {
                throw new IllegalArgumentException("User already exists.");
            }
            User user = new User(userId, isAdmin ? User.UserType.ADMIN : User.UserType.USER, 100);
            users.put(userId, user);
            userRepository.saveUser(Mapper.toDTO(user));
            emitAllUsers();
        }).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }

    @Override
    public Single<UserDTO> logIn(String userId) {
        return Single.fromCallable(() -> {
            Optional<User> userOpt = users.values().stream()
                    .filter(user -> user.getId().equals(userId))
                    .findFirst();
            if (userOpt.isEmpty()) {
                userRepository.findUserById(userId)
                        .ifPresentOrElse(userDTO -> users.put(userDTO.id(), Mapper.toDomain(userDTO)),
                                () -> {
                                    throw new IllegalArgumentException("User not found.");
                                });
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
            User user = users.get(userId);
            if (user == null) {
                emitter.onError(new IllegalArgumentException("User not found."));
                return;
            }else if(user.getCredit() == 0){
                emitter.onError(new IllegalArgumentException("User has no credit."));
                return;
            }

            EBike bike = bikes.get(bikeId);
            if (bike == null) {
                emitter.onError(new IllegalArgumentException("Bike not found."));
                return;
            }else if (bike.getBatteryLevel() == 0){
                emitter.onError(new IllegalArgumentException("Bike has no battery."));
                return;
            }

            synchronized (bike) {
                if (!bike.isAvailable()) {
                    emitter.onError(new IllegalStateException("Bike is not available."));
                    return;
                }
                bike.setState(EBike.EBikeState.IN_USE);
            }

            // Start ride
            Ride ride = new Ride(rideId, user, bike);
            ride.start();
            rideRepository.saveRide(Mapper.toDTO(ride));
            RideSimulation simulation = new RideSimulation(ride, user);
            rideEntries.put(rideId, new RideEntry(ride, simulation));

            // Subscribe to ride updates from the RideSimulation
            simulation.getRideObservable()
                    .observeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                    .subscribe(
                            rideDTO -> {
                                // Emit every ride update to the subscriber
                                rideRepository.updateRide(Mapper.toDTO(ride));
                                eBikeRepository.updateEBike(Mapper.toDTO(bike));
                                userRepository.updateUser(Mapper.toDTO(user));
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
                                rideRepository.updateRide(Mapper.toDTO(ride));
                                eBikeRepository.updateEBike(Mapper.toDTO(bike));
                                userRepository.updateUser(Mapper.toDTO(user));
                                emitter.onComplete();
                            }
                    );

            // Start the ride simulation process
            simulation.startSimulation();

            // Emit the current bike status immediately
            emitter.onNext(new RideDTO(
                    bike.getId(),
                    bike.getLocation().x(),
                    bike.getLocation().y(),
                    user.getCredit(),
                    bike.getBatteryLevel()
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
            RideEntry rideEntry = rideEntries.get(rideId);
            if (rideEntry == null) {
                emitter.onError(new IllegalArgumentException("Ride not found."));
                return;
            }

            if (!rideEntry.ride().getUser().getId().equals(userId) ||
                    !rideEntry.ride().getEbike().getId().equals(bikeId)) {
                emitter.onError(new IllegalArgumentException("Ride does not match user and bike."));
                return;
            }

            // Stop simulation
            rideEntry.simulation().stopSimulation();

            // Update bike state
            EBike bike = rideEntry.ride().getEbike();
            synchronized (bike) {
                bike.setState(EBike.EBikeState.AVAILABLE);
//                bike.rechargeBattery(); // Assuming ride completion recharges the bike
            }

            // End ride
            rideEntry.ride().end();

            // Update repositories
            rideRepository.updateRide(Mapper.toDTO(rideEntry.ride()));
            eBikeRepository.updateEBike(Mapper.toDTO(bike));
            userRepository.updateUser(Mapper.toDTO(rideEntry.ride().getUser()));

            emitAllBikes();
            emitAvailableBikes();
            emitAllUsers();

            emitter.onComplete();
        }).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }

    @Override
    public Observable<RideDTO> observeRide(String userId, String rideId) {
        RideEntry rideEntry = rideEntries.get(rideId);
        if (rideEntry == null) {
            return Observable.error(new IllegalArgumentException("Ride not found."));
        }
        Ride ride = rideEntry.ride();
        if (!ride.getUser().getId().equals(userId)) {
            return Observable.error(new IllegalArgumentException("Ride does not belong to the user."));
        }
        return rideEntry.simulation().getRideObservable()
                .observeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }

    @Override
    public Single<UserDTO> rechargeCredit(String userId, int amount) {
        return Single.fromCallable(() -> {
            User user = users.get(userId);
            if (user == null) {
                userRepository.findUserById(userId)
                        .ifPresentOrElse(userDTO -> users.put(userDTO.id(), Mapper.toDomain(userDTO)),
                                () -> {
                                    throw new IllegalArgumentException("User not found.");
                                });
            }
            synchronized (Objects.requireNonNull(user)) {
                user.increaseCredit(amount);
            }
            userRepository.updateUser(Mapper.toDTO(user));
            emitAllUsers();
            return mapToDTO(user);
        }).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }

    // ------------------- Helper Methods -------------------

    private void handleRideCompletion(String rideId) {

        RideEntry rideEntry = rideEntries.get(rideId);
        if (rideEntry != null) {
            rideEntries.remove(rideId);
            emitAllBikes();
            emitAvailableBikes();
        }
    }

    private void emitAllBikes() {
        log("Emitting all bikes");
        Collection<EBikeDTO> bikeDTOs = new ArrayList<>();
        for (EBike bike : bikes.values()) {
            bikeDTOs.add(mapToDTO(bike));
        }
        allBikesSubject.onNext(bikeDTOs);
    }

    private void emitAvailableBikes() {
        log("Emitting available bikes");
        Collection<EBikeDTO> availableDTOs = new ArrayList<>();
        for (EBike bike : bikes.values()) {
            if (bike.isAvailable() ) {
                availableDTOs.add(mapToDTO(bike));
            }
        }
        availableBikesSubject.onNext(availableDTOs);
    }

    private void emitAllUsers() {
        log("Emitting all users");
        Collection<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users.values()) {
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