package sap.ass01.hexagonal.domain.model;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

import java.util.concurrent.TimeUnit;

public class RideSimulation {
    private final Ride ride;
    private final User user;
    private final PublishSubject<Ride> rideUpdates = PublishSubject.create();
    private volatile boolean stopped = false;
    private long lastTimeChangedDir = System.currentTimeMillis();

    public RideSimulation(Ride ride, User user) {
        this.ride = ride;
        this.user = user;
    }

    public Observable<Ride> getRideObservable() {
        return rideUpdates.hide();
    }

    public void startSimulation() {
        if (ride.isOngoing()) {
            Observable.interval(100, TimeUnit.MILLISECONDS) // Emit every 100ms
                    .takeUntil(tick -> stopped)            // Stop if the simulation is stopped
                    .observeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                    .subscribe(
                            tick -> updateRide(),
                            throwable -> {
                                // Handle error if necessary
                                rideUpdates.onError(throwable);
                            },
                            this::completeSimulation  // Complete the simulation when done
                    );
        }
    }

    private void updateRide() {
        EBike bike = ride.getEbike();

        synchronized (bike) {
            if(bike.getBatteryLevel() == 0) {
                ride.end();
                stopSimulation();
                completeSimulation();
            }

            if(user.getCredit() == 0) {
                ride.end();
                stopSimulation();
                bike.setState(EBike.EBikeState.AVAILABLE);
                completeSimulation();
            }
            // Simulate movement and battery usage
            V2d direction = bike.getDirection();
            double speed = 0.5;  // Set speed to a constant value for simplicity
            V2d movement = direction.mul(speed);
            bike.setLocation(bike.getLocation().sum(movement));

            // Boundary checks to reverse direction
            if (bike.getLocation().x() > 200 || bike.getLocation().x() < -200) {
                bike.setDirection(new V2d(-direction.x(), direction.y()));
            }
            if (bike.getLocation().y() > 200 || bike.getLocation().y() < -200) {
                bike.setDirection(new V2d(direction.x(), -direction.y()));
            }

            // Change direction every 500ms
            long elapsedTimeSinceLastChangeDir = System.currentTimeMillis() - lastTimeChangedDir;
            if (elapsedTimeSinceLastChangeDir > 500) {
                double angle = Math.random() * 60 - 30;
                bike.setDirection(direction.rotate(angle));
                lastTimeChangedDir = System.currentTimeMillis();
            }

            // Decrease battery and user credit
            bike.decreaseBattery(1);
            user.decreaseCredit(1);



            // Emit updated ride information

            rideUpdates.onNext(ride);
        }
    }

    private void completeSimulation() {
        // Emit the completion of the simulation
        rideUpdates.onComplete();
    }

    public void stopSimulation() {
        stopped = true;
    }
}