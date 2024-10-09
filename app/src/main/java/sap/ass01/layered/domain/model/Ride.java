package sap.ass01.layered.domain.model;

import java.util.Date;
import java.util.Optional;

public class Ride {

    private Date startedDate;
    private Optional<Date> endDate;
    private User user;
    private EBike ebike;
    private boolean ongoing;
    private String id;

    public Ride(String id, User user, EBike ebike) {
        this.id = id;
        this.startedDate = new Date();
        this.endDate = Optional.empty();
        this.user = user;
        this.ebike = ebike;
        this.ongoing = false;
    }

    public String getId() {
        return id;
    }

    public void start() {
        ongoing = true;
    }

    public void end() {
        endDate = Optional.of(new Date());
        ongoing = false;
    }

    public boolean isOngoing() {
        return this.ongoing;
    }

    public Date getStartedDate() {
        return startedDate;
    }

    public Optional<Date> getEndDate() {
        return endDate;
    }

    public User getUser() {
        return user;
    }

    public EBike getEBike() {
        return ebike;
    }

    public String toString() {
        return "{ id: " + this.id + ", user: " + user.getId() + ", bike: " + ebike.getId() + " }";
    }
}