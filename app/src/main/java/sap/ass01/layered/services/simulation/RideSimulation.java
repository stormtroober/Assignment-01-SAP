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
    private final PublishSubject<RideDTO> rideUpdates = PublishSubject.create();
    private volatile boolean stopped = false;

    public RideSimulation(Ride ride, User user) {
        this.ride = ride;
        this.user = user;
    }

    public Observable<RideDTO> getRideObservable() {
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
            // Simulate movement and battery usage
            V2d direction = bike.getDirection();
            double speed = 1.0;  // Set speed to a constant value for simplicity
            V2d movement = direction.mul(speed);
            bike.setLocation(bike.getLocation().sum(movement));

            // Boundary checks to reverse direction
            if (bike.getLocation().x() > 200 || bike.getLocation().x() < -200) {
                bike.setDirection(new V2d(-direction.x(), direction.y()));
            }
            if (bike.getLocation().y() > 200 || bike.getLocation().y() < -200) {
                bike.setDirection(new V2d(direction.x(), -direction.y()));
            }

            // Randomly change direction (5% chance)
            if (Math.random() < 0.05) {
                bike.setDirection(new V2d(Math.random() - 0.5, Math.random() - 0.5).getNormalized());
            }

            // Decrease battery and user credit
            bike.decreaseBattery(1);
            user.decreaseCredit(1);

            // Emit updated ride information
            RideDTO rideDTO = new RideDTO(
                    bike.getId(),
                    bike.getLocation().x(),
                    bike.getLocation().y(),
                    user.getCredit(),
                    bike.getBatteryLevel()
            );
            rideUpdates.onNext(rideDTO);
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
