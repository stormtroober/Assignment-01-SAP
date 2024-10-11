package sap.ass01.layered.services.simulation;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import sap.ass01.layered.domain.model.EBike;
import sap.ass01.layered.domain.model.Ride;
import sap.ass01.layered.domain.model.User;
import sap.ass01.layered.domain.model.V2d;
import sap.ass01.layered.services.dto.RideDTO;

import java.util.concurrent.TimeUnit;

public class RideSimulation {
    private final Ride ride;
    private final User user;
    private final PublishSubject<RideDTO> rideUpdates;
    private final Observable<RideDTO> rideObservable;
    private volatile boolean stopped = false;

    public RideSimulation(Ride ride, User user) {
        this.ride = ride;
        this.user = user;
        this.rideUpdates = PublishSubject.create();
        this.rideObservable = rideUpdates.hide().publish().autoConnect();
    }

    public Observable<RideDTO> getRideObservable() {
        return rideObservable;
    }

    public void startSimulation() {
        if (ride.isOngoing()) {
            Observable.interval(0, 100, TimeUnit.MILLISECONDS)
                    .takeUntil(tick -> stopped)
                    .observeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                    .subscribe(tick -> updateRide(),
                            Throwable::printStackTrace,
                            this::completeSimulation);
        }
    }

    private void updateRide() {
        EBike bike = ride.getEbike();
        bike.setSpeed(1.0);

        // Update location
        synchronized (bike) {
            V2d direction = bike.getDirection();
            double speed = bike.getSpeed();
            V2d movement = direction.mul(speed);
            bike.setLocation(bike.getLocation().sum(movement));

            // Boundary checks
            var location = bike.getLocation();
            if (location.x() > 200 || location.x() < -200) {
                bike.setDirection(new V2d(-direction.x(), direction.y()));
            }
            if (location.y() > 200 || location.y() < -200) {
                bike.setDirection(new V2d(direction.x(), -direction.y()));
            }

            // Random direction change
            if (Math.random() < 0.05) { // Approximately every 2 ticks
                bike.setDirection(new V2d(Math.random() - 0.5, Math.random() - 0.5).getNormalized());
            }

            // Decrease battery
            bike.decreaseBattery(1);

            // Decrease user credit
            user.decreaseCredit(1);
        }

        // Emit update
        RideDTO rideDTO = new RideDTO(
                bike.getId(),
                bike.getLocation().x(),
                bike.getLocation().y(),
                user.getCredit(),
                bike.getBatteryLevel()
        );
        rideUpdates.onNext(rideDTO);
    }

    private void completeSimulation() {
        rideUpdates.onComplete();
    }

    public void stopSimulation() {
        stopped = true;
    }
}