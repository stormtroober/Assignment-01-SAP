package sap.ass01.bbom;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class EBikeService {
    private final ConcurrentHashMap<String, EBike> bikes = new ConcurrentHashMap<>();
    private final HashMap<String, User> users = new HashMap<>();
    private final HashMap<String, Ride> rides = new HashMap<>();
    private final List<Listener> listeners = new ArrayList<>();
    private int rideId;

    public void registerListener(Listener listener) {
        listeners.add(listener);
    }

    private void notifyModelChanged() {
        for (Listener listener : listeners) {
            listener.notifyModelChanged();
        }
    }

    private void notifyRideStart(Ride ride) {
        for (Listener listener : listeners) {
            listener.notifyRideStart(ride);
        }
    }

    public EBikeService(){
        addUser("u1");
        addEBike("b1", new P2d(0,0));
        rideId = 0;
    }

    public void addEBike(String id, P2d loc) {
        EBike bike = new EBike(id);
        bike.updateLocation(loc);
        bikes.put(id, bike);
        System.out.println("Added new EBike " + bike);
        notifyModelChanged();
    }

    public void addUser(String id) {
        User user = new User(id);
        user.rechargeCredit(100);
        users.put(id, user);
        System.out.println("Added new User " + user);
        notifyModelChanged();
    }

    public void startNewRide(String userId, String bikeId) {
        rideId++;
        String idRide = "ride-" + rideId;

        var b = bikes.get(bikeId);
        var u = users.get(userId);
        var ride = new Ride(idRide, u, b);
        b.updateState(EBike.EBikeState.IN_USE);
        rides.put(idRide, ride);
        notifyRideStart(ride);

        System.out.println("Started new Ride " + ride);
        notifyModelChanged();
    }

    public void endRide(String rideId) {
        var r = rides.get(rideId);
        r.end();
        rides.remove(rideId);
        notifyModelChanged();
    }

    public EBike getEBike(String id) {
        return bikes.get(id);
    }

    public User getUser(String id) {
        return users.get(id);
    }

    public Enumeration<EBike> getEBikes(){
        return bikes.elements();
    }

    public Collection<User> getUsers(){
        return users.values();
    }
}
