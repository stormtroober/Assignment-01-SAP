package sap.ass01.layered.domain.model;

public class EBike {
    public enum EBikeState { AVAILABLE, IN_USE, MAINTENANCE }

    private final String id;
    private volatile EBikeState state;
    private volatile P2d location;
    private volatile V2d direction;
    private volatile double speed; // Units per simulation tick
    private volatile int batteryLevel; // 0..100

    public EBike(String id, double x, double y) {
        this.id = id;
        this.state = EBikeState.AVAILABLE;
        this.location = new P2d(x, y);
        this.direction = new V2d(1, 0); // Initial direction
        this.speed = 0; // Default speed
        this.batteryLevel = 100;
    }


    public String getId() { return id; }

    public synchronized EBikeState getState() { return state; }
    public synchronized void setState(EBikeState state) { this.state = state; }

    public synchronized P2d getLocation() { return location; }
    public synchronized void setLocation(P2d location) { this.location = location; }

    public synchronized V2d getDirection() { return direction; }
    public synchronized void setDirection(V2d direction) { this.direction = direction; }

    public synchronized double getSpeed() { return speed; }
    public synchronized void setSpeed(double speed) { this.speed = speed; }

    public synchronized int getBatteryLevel() { return batteryLevel; }
    public synchronized void decreaseBattery(int amount) {
        this.batteryLevel = Math.max(this.batteryLevel - amount, 0);
        if (this.batteryLevel == 0) {
            this.state = EBikeState.MAINTENANCE;
        }
    }

    public synchronized void rechargeBattery() {
        this.batteryLevel = 100;
        this.state = EBikeState.AVAILABLE;
    }

    public synchronized boolean isAvailable() {
        return this.state == EBikeState.AVAILABLE;
    }

    @Override
    public String toString() {
        return String.format("EBike{id='%s', location=%s, batteryLevel=%d%%, state='%s'}",
                id, location, batteryLevel, state);
    }
}