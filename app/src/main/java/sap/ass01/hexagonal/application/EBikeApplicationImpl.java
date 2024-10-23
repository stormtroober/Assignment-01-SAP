package sap.ass01.hexagonal.application;

import io.reactivex.rxjava3.core.Observable;
import sap.ass01.hexagonal.application.ports.EBikeApplication;
import sap.ass01.hexagonal.application.ports.entities.EBikeDTO;
import sap.ass01.hexagonal.domain.model.EBikeState;
import sap.ass01.hexagonal.application.ports.entities.RideDTO;
import sap.ass01.hexagonal.application.ports.entities.UserDTO;
import sap.ass01.hexagonal.application.ports.EBikeRepository;
import sap.ass01.hexagonal.application.ports.RideRepository;
import sap.ass01.hexagonal.application.ports.UserRepository;
import sap.ass01.hexagonal.domain.model.EBike;
import sap.ass01.hexagonal.domain.model.Ride;
import sap.ass01.hexagonal.domain.model.RideSimulation;
import sap.ass01.hexagonal.domain.model.User;
import sap.ass01.hexagonal.application.ports.mapper.Mapper;


import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class EBikeApplicationImpl implements EBikeApplication {

    private final EBikeRepository eBikeRepository;
    private final UserRepository userRepository;
    private final RideRepository rideRepository;

    private final ConcurrentHashMap<String, EBike> bikes = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, RideSimulation> rideSim = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Ride> rides = new ConcurrentHashMap<>();

    public EBikeApplicationImpl(EBikeRepository eBikeRepository, UserRepository userRepository, RideRepository rideRepository) {
        this.eBikeRepository = eBikeRepository;
        this.userRepository = userRepository;
        this.rideRepository = rideRepository;

        eBikeRepository.findAllEBikes().forEach(eBikeDTO -> bikes.put(eBikeDTO.id(), Mapper.toModel(eBikeDTO)));
        userRepository.findAllUsers().forEach(userDTO -> users.put(userDTO.id(), Mapper.toModel(userDTO)));
    }

    @Override
    public Optional<EBikeDTO> addEbike(String id, double x, double y) {
        if (bikes.containsKey(id)) {
            return Optional.empty();
        }
        EBike bike = new EBike(id, x, y, EBikeState.AVAILABLE, 100);
        bikes.put(id,bike);
        eBikeRepository.saveEBike(Mapper.toDTO(bike));
        return Optional.of(Mapper.toDTO(bike));
    }

    @Override
    public Collection<EBikeDTO> getBikes() {
        return bikes.values().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public Collection<UserDTO> getUsers() {
        return users.values().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public Optional<EBikeDTO> rechargeBike(String id) {
        EBike existingBike = bikes.get(id);
        if (existingBike != null) {
            existingBike.rechargeBattery();
            eBikeRepository.updateEBike(Mapper.toDTO(existingBike));
            return Optional.of(Mapper.toDTO(existingBike));
        }
        return Optional.empty();
    }

    @Override
    public boolean addUser(String userId, boolean isAdmin) {
        if (users.containsKey(userId)) {
            return false;
        }
        User user = new User(userId, isAdmin? User.UserType.ADMIN : User.UserType.USER, 100);
        users.put(userId, user);
        userRepository.saveUser(Mapper.toDTO(user));
        return true;
    }


    @Override
    public Optional<EBikeDTO> getEbike(String id) {
        Optional<EBikeDTO> bike = Optional.ofNullable(bikes.get(id)).map(Mapper::toDTO);
        if(bike.isEmpty()){
            bike = eBikeRepository.findEBikeById(id);
            bike.ifPresent(eBikeDTO -> bikes.put(eBikeDTO.id(), Mapper.toModel(eBikeDTO)));
        }
        return bike;
    }

    @Override
    public Optional<UserDTO> getUser(String userId) {
        Optional<UserDTO> user = Optional.ofNullable(users.get(userId)).map(Mapper::toDTO);
        if(user.isEmpty()){
            user = userRepository.findUserById(userId);
            user.ifPresent(userDTO -> users.put(userDTO.id(), Mapper.toModel(userDTO)));
        }
        return user;
    }

    @Override
    public Optional<RideDTO> getRide(String rideId) {
        Optional<Ride> ride = Optional.ofNullable(rides.get(rideId));
        if(ride.isEmpty()){
            ride = rideRepository.findRideById(rideId).map(rideDTO -> {
                UserDTO user = getUser(rideDTO.user().id()).orElse(null);
                EBikeDTO bike = getEbike(rideDTO.ebike().id()).orElse(null);
                if (user != null && bike != null) {
                    Ride rideTmp = Mapper.toModel(rideDTO);
                    rides.put(rideTmp.getId(), rideTmp);
                    return rideTmp;
                }
                return null;
            });
        }
        return ride.map(Mapper::toDTO);
    }

    @Override
    public Optional<UserDTO> rechargeCredit(String id, int credit) {
        User existingUser = users.get(id);
        if (existingUser != null) {
            existingUser.increaseCredit(credit);
            userRepository.updateUser(Mapper.toDTO(existingUser));
            return Optional.of(Mapper.toDTO(existingUser));
        }
        return Optional.empty();
    }


    @Override
    public Observable<RideDTO> startRide(String rideId, String userId, String bikeId) {
        return Observable.<RideDTO>create(emitter -> {
            if(rides.keySet().stream().anyMatch(r -> r.equals(rideId))){
                emitter.onError(new IllegalArgumentException("Ride already exists"));
                return;
            }
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
                    bike.setState(EBikeState.IN_USE);
                    Ride ride = new Ride(rideId, user, bike);
                    RideSimulation rideSimulation = new RideSimulation(ride, user);
                    rideSim.put(rideId, rideSimulation);
                    rides.put(rideId, ride);
                    rideRepository.saveRide(Mapper.toDTO(ride));


                    ride.start();
                    // Start the ride simulation process
                    rideSimulation.startSimulation();

                    // Emit the current bike status immediately
                    emitter.onNext(Mapper.toDTO(ride));


                }
            }

        });
    }



    @Override
    public boolean endRide(RideDTO rideDTO) {
        Ride ride = rides.get(rideDTO.id());

        if (ride != null) {
            RideSimulation simulation = rideSim.get(ride.getId());
            if (simulation != null) {
                System.out.println("Stopping simulation from end");
                simulation.stopSimulation();
            }
            if(ride.getEbike().getState().equals(EBikeState.IN_USE)){
                ride.getEbike().setState(EBikeState.AVAILABLE);
            }
            ride.end();

            rides.remove(ride.getId());
            rideSim.remove(ride.getId());
            return true;
        }
        return false;
    }

    @Override
    public Observable<RideDTO> getRideSimulationObservable(String id) {
        return rides.keySet().stream()
                .filter(r -> r.equals(id))
                .findFirst()
                .map(r -> {
                    RideSimulation rideSimulation = rideSim.get(r);
                    return rideSimulation.getRideObservable()
                            .doOnNext(rideDTO -> {
                                Optional<RideDTO> ride = getRide(r);
                                ride.ifPresent(r1 -> {
                                    rideRepository.updateRide(r1);
                                    userRepository.updateUser(r1.user());
                                    eBikeRepository.updateEBike(r1.ebike());
                                });

                            })
                            .doOnError(throwable -> {
                                // Handle error
                            })
                            .doOnComplete(() -> {
                                System.out.println("Savingggg");

                                Optional<RideDTO> ride = getRide(r);
                                ride.ifPresent(r1 -> {
                                    rideRepository.updateRide(r1);
                                    userRepository.updateUser(r1.user());
                                    if(r1.ebike().state().equals(EBikeState.IN_USE.name())){
                                        EBikeDTO bike = new EBikeDTO(r1.ebike().id(), r1.ebike().x(), r1.ebike().y(), EBikeState.AVAILABLE.name(), r1.ebike().battery());
                                        eBikeRepository.updateEBike(bike);
                                    }else{
                                        eBikeRepository.updateEBike(r1.ebike());
                                    }

                                });
                            })
                            .map(Mapper::toDTO)
                            .hide();
                })
                .orElse(null);
    }






}
