package sap.ass01.hexagonal.domain.model;

import java.util.Date;
import java.util.Optional;

public class Ride {
    private final String id;
    private final User user;
    private final EBike ebike;
    private final Date startTime;
    private volatile Optional<Date> endTime;
    private volatile boolean ongoing;

    public Ride(String id, User user, EBike ebike) {
        this.id = id;
        this.user = user;
        this.ebike = ebike;
        this.startTime = new Date();
        this.endTime = Optional.empty();
        this.ongoing = false;
    }

    public String getId() { return id; }
    public User getUser() { return user; }
    public EBike getEbike() { return ebike; }
    public Date getStartTime() { return startTime; }
    public Optional<Date> getEndTime() { return endTime; }
    public boolean isOngoing() { return ongoing; }

    public void start() { this.ongoing = true; }

    public void end() {
        if (this.ongoing) {
            this.endTime = Optional.of(new Date());
            this.ongoing = false;
        }
    }

    @Override
    public String toString() {
        return String.format("Ride{id='%s', user='%s', ebike='%s', ongoing=%s}",
                id, user.getId(), ebike.getId(), ongoing);
    }
}