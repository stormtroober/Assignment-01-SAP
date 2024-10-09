package sap.ass01.layered.services.impl;

import sap.ass01.layered.domain.model.EBike;
import sap.ass01.layered.domain.model.Ride;
import sap.ass01.layered.domain.model.RideEntry;
import sap.ass01.layered.domain.model.User;
import sap.ass01.layered.services.dto.EBikeDTO;
import sap.ass01.layered.services.dto.UserDTO;
import sap.ass01.layered.services.Services.AdminService;
import sap.ass01.layered.services.Services.LoginService;
import sap.ass01.layered.services.Services.UserService;
import sap.ass01.layered.services.simulation.RideUpdateListener;
import sap.ass01.layered.services.simulation.RideSimulation;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class BusinessImpl implements AdminService, LoginService, UserService {
    private final ConcurrentHashMap<String, EBike> bikes = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, RideEntry> rideEntries = new ConcurrentHashMap<>();
    private int rideId;

    public BusinessImpl() {
    }

    @Override
    public CompletableFuture<EBikeDTO> createEBike(String bikeId, int x, int y) {
        return CompletableFuture.supplyAsync(() -> {
            EBike bike = new EBike(bikeId, x, y);
            bikes.put(bikeId, bike);
            return new EBikeDTO(bike.getId(), bike.getLocation().x(), bike.getLocation().y(), bike.getBatteryLevel(), bike.getState().name());
        });
    }

    @Override
    public CompletableFuture<Void> signIn(String name, Boolean admin) {
        return CompletableFuture.runAsync(() -> {
            User user = new User(name, admin ? User.UserType.ADMIN : User.UserType.USER);
            users.put(name, user);
        });
    }

    @Override
    public CompletableFuture<UserDTO> logIn(String name) {
        return CompletableFuture.supplyAsync(() -> {
            User user = users.get(name);
            return new UserDTO(user.getId(), user.getCredit(), user.getPermission().name());
        });
    }

    @Override
    public CompletableFuture<RideUpdateListener> startRide(String userId, String bikeId) {
        return CompletableFuture.supplyAsync(() -> {
            User user = users.get(userId);
            EBike bike = bikes.get(bikeId);
            Ride ride = new Ride(String.valueOf(rideId++), user, bike);
            ride.start();

            RideUpdateListener rideUpdateListener = rideDTO -> {
                // Notify the presentation layer with the updated position
                System.out.println("Position updated: " + rideDTO.x() + " " + rideDTO.y());
                // Here you can add code to refresh the graphics
            };

            RideSimulation rideSimulation = new RideSimulation(ride, user, rideUpdateListener);
            rideEntries.put(ride.getId(), new RideEntry(ride, rideSimulation));
            rideSimulation.start();

            return rideUpdateListener;
        });
    }

    @Override
    public CompletableFuture<Void> endRide(String userId, String bikeId) {
        return CompletableFuture.runAsync(() -> {
            RideEntry rideEntry = rideEntries.values().stream()
                    .filter(entry -> entry.ride().getUser().getId().equals(userId) && entry.ride().getEBike().getId().equals(bikeId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Ride not found"));
            rideEntry.ride().end();
            rideEntry.simulation().stopSimulation();
            rideEntries.remove(rideEntry.ride().getId());
        });
    }

    public CompletableFuture<UserDTO> getUser(String id) {
        return CompletableFuture.supplyAsync(() -> {
            User user = users.get(id);
            return new UserDTO(user.getId(), user.getCredit(), user.getPermission().name());
        });
    }

    @Override
    public CompletableFuture<Collection<EBikeDTO>> getAllEBikes() {
        return CompletableFuture.supplyAsync(() -> {
            Collection<EBikeDTO> bikeDTOs = new ArrayList<>();
            for (EBike bike : bikes.values()) {
                bikeDTOs.add(new EBikeDTO(bike.getId(), bike.getLocation().x(), bike.getLocation().y(), bike.getBatteryLevel(), bike.getState().name()));
            }
            return bikeDTOs;
        });
    }

    @Override
    public CompletableFuture<Collection<EBikeDTO>> getAvailableEBikes() {
        return CompletableFuture.supplyAsync(() -> {
            Collection<EBikeDTO> bikeDTOs = new ArrayList<>();
            for (EBike bike : bikes.values()) {
                if (bike.getState() == EBike.EBikeState.AVAILABLE) {
                    bikeDTOs.add(new EBikeDTO(bike.getId(), bike.getLocation().x(), bike.getLocation().y(), bike.getBatteryLevel(), bike.getState().name()));
                }
            }
            return bikeDTOs;
        });
    }
}