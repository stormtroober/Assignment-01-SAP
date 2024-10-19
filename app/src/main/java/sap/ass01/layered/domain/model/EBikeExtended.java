// File: EBikeExtended.java
package sap.ass01.layered.domain.model;

public class EBikeExtended {
    private EBike bike;
    private String color;  // Color information added by plugin

    public EBikeExtended(EBike bike) {
        this.bike = bike;
    }

    public EBike getBike() {
        return bike;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}