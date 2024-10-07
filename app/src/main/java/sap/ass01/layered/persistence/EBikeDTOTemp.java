package sap.ass01.layered.persistence;

import sap.ass01.layered.domain.EBike;
import sap.ass01.layered.domain.P2d;
import sap.ass01.layered.domain.V2d;

public class EBikeDTOTemp {
    private String id;
//    public enum EBikeState { AVAILABLE, IN_USE, MAINTENANCE}
//    private EBike.EBikeState state;
//    private P2d loc;
//    private V2d direction;
    private double speed;
    private int batteryLevel;  /* 0..100 */

    public EBikeDTOTemp(String id
//            , EBike.EBikeState state
//            , P2d loc, V2d direction
            , double speed, int batteryLevel) {
        this.id = id;
//        this.state = state;
//        this.loc = loc;
//        this.direction = direction;
        this.speed = speed;
        this.batteryLevel = batteryLevel;
    }

    public String getId() {
        return id;
    }

//    public EBike.EBikeState getState() {
//        return state;
//    }

//    public P2d getLoc() {
//        return loc;
//    }
//
//    public V2d getDirection() {
//        return direction;
//    }

    public double getSpeed() {
        return speed;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }
}
