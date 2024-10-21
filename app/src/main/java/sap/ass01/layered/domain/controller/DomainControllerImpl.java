package sap.ass01.layered.domain.controller;

import sap.ass01.layered.domain.mapper.Mapper;
import sap.ass01.layered.domain.model.EBike;
import sap.ass01.layered.domain.model.Ride;
import sap.ass01.layered.domain.model.User;
import sap.ass01.layered.persistence.EBikeRepositoryImpl;
import sap.ass01.layered.persistence.RideRepositoryImpl;
import sap.ass01.layered.persistence.UserRepositoryImpl;
import sap.ass01.layered.persistence.repository.EBikeRepository;
import sap.ass01.layered.persistence.repository.RideRepository;
import sap.ass01.layered.persistence.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class DomainControllerImpl implements DomainController{

    private final ConcurrentHashMap<String, EBike> bikes = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Ride> rides = new ConcurrentHashMap<>();

    private final EBikeRepository eBikeRepository;
    private final UserRepository userRepository;
    private final RideRepository rideRepository;

    public DomainControllerImpl() {
        eBikeRepository = new EBikeRepositoryImpl();
        userRepository = new UserRepositoryImpl();
        rideRepository = new RideRepositoryImpl();

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
                rides.put(rideTmp.getId(), rideTmp);
            }
        });
    }

    @Override
    public Collection<EBike> getBikes() {
        return bikes.values();
    }

    @Override
    public Collection<User> getUsers() {
        return users.values();
    }

    @Override
    public Collection<Ride> getRide() {
        return rides.values();
    }

    @Override
    public void addBike(EBike bike) {
        bikes.put(bike.getId(), bike);
        eBikeRepository.saveEBike(Mapper.toDTO(bike));
    }

    @Override
    public Optional<EBike> getBikeById(String bikeId) {
        Optional<EBike> ebike = Optional.ofNullable(bikes.get(bikeId));
        if (ebike.isEmpty()) {
            ebike = eBikeRepository.findEBikeById(bikeId).map(Mapper::toDomain);
            ebike.ifPresent(eBike -> bikes.put(eBike.getId(), eBike));
        }
        return ebike;
    }

    @Override
    public void updateBike(EBike bike) {
        eBikeRepository.updateEBike(Mapper.toDTO(bike));
    }

    @Override
    public void addUser(User user) {
        users.put(user.getId(), user);
        userRepository.saveUser(Mapper.toDTO(user));
    }

    @Override
    public Optional<User> getUserById(String userId) {
        Optional<User> user = Optional.ofNullable(users.get(userId));
        if (user.isEmpty()) {
            user = userRepository.findUserById(userId).map(Mapper::toDomain);
            user.ifPresent(u -> users.put(u.getId(), u));
        }
        return user;
    }

    @Override
    public void updateUser(User user) {
        userRepository.updateUser(Mapper.toDTO(user));
    }

    @Override
    public void addRide(Ride ride) {
        rides.put(ride.getId(), ride);
        rideRepository.saveRide(Mapper.toDTO(ride));
    }

    @Override
    public Optional<Ride> getRideById(String rideId) {
        Optional<Ride> ride = Optional.ofNullable(rides.get(rideId));
        if(ride.isEmpty()){
            ride = rideRepository.findRideById(rideId).map(rideDTO -> {
                User user = getUserById(rideDTO.user().id()).orElse(null);
                EBike bike = getBikeById(rideDTO.bike().id()).orElse(null);
                if (user != null && bike != null) {
                    Ride rideTmp = Mapper.toDomain(rideDTO, user, bike);
                    rides.put(rideTmp.getId(), rideTmp);
                    return rideTmp;
                }
                return null;
            });
        }

        return ride;
    }

    @Override
    public void updateRide(Ride ride) {
        rideRepository.updateRide(Mapper.toDTO(ride));
    }
}
