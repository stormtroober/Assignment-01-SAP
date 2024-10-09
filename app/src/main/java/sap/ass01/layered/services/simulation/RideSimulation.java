package sap.ass01.layered.services.simulation;



import sap.ass01.layered.domain.model.P2d;
import sap.ass01.layered.domain.model.Ride;
import sap.ass01.layered.domain.model.User;
import sap.ass01.layered.domain.model.V2d;
import sap.ass01.layered.services.dto.RideDTO;


public class RideSimulation extends Thread {

    private Ride ride;
    private User user;
    private volatile boolean stopped;
    private RideUpdateListener rideUpdateListener;

    public RideSimulation(Ride ride, User user, RideUpdateListener rideUpdateListener) {
        this.ride = ride;
        this.user = user;
        this.rideUpdateListener = rideUpdateListener;
        stopped = false;
    }

    public void run() {
        var b = ride.getEBike();
        b.updateSpeed(1);

        var lastTimeDecreasedCredit = System.currentTimeMillis();
        user.decreaseCredit(1);

        var lastTimeChangedDir = System.currentTimeMillis();

        while (!stopped) {

            /* update pos */
            var l = b.getLocation();
            var d = b.getDirection();
            var s = b.getSpeed();
            b.updateLocation(l.sum(d.mul(s)));
            l = b.getLocation();
            if (l.x() > 200 || l.x() < -200) {
                b.updateDirection(new V2d(-d.x(), d.y()));
                if (l.x() > 200) {
                    b.updateLocation(new P2d(200, l.y()));
                } else {
                    b.updateLocation(new P2d(-200, l.y()));
                }
            }
            if (l.y() > 200 || l.y() < -200) {
                b.updateDirection(new V2d(d.x(), -d.y()));
                if (l.y() > 200) {
                    b.updateLocation(new P2d(l.x(), 200));
                } else {
                    b.updateLocation(new P2d(l.x(), -200));
                }
            }

            /* change dir randomly */
            var elapsedTimeSinceLastChangeDir = System.currentTimeMillis() - lastTimeChangedDir;
            if (elapsedTimeSinceLastChangeDir > 500) {
                double angle = Math.random() * 60 - 30;
                b.updateDirection(d.rotate(angle));
                lastTimeChangedDir = System.currentTimeMillis();
            }

            /* update credit */
            var elapsedTimeSinceLastDecredit = System.currentTimeMillis() - lastTimeDecreasedCredit;
            if (elapsedTimeSinceLastDecredit > 1000) {
                user.decreaseCredit(1);
                lastTimeDecreasedCredit = System.currentTimeMillis();
            }

            /* Notify update */
            if (rideUpdateListener != null) {
                rideUpdateListener.onRideUpdate(new RideDTO(b.getId(), b.getLocation().x(), b.getLocation().y(), user.getCredit(), b.getBatteryLevel()));
            }

            try {
                Thread.sleep(20);
            } catch (Exception ex) {
            }
        }
    }

    public void stopSimulation() {
        stopped = true;
        interrupt();
    }
}