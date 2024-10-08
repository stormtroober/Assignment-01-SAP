package sap.ass01.layered.persistence.DTO;

public class EBikeDTO {
    private final String id;
    private final String state;
    private final double x;
    private final double y;
    private final double directionX;
    private final double directionY;
    private final double speed;
    private final int batteryLevel;

    public EBikeDTO(final String id, final String state, final double x, final double y, final double directionX, final double directionY, final double speed, final int batteryLevel) {
        this.id = id;
        this.state = state;
        this.x = x;
        this.y = y;
        this.directionX = directionX;
        this.directionY = directionY;
        this.speed = speed;
        this.batteryLevel = batteryLevel;
    }

    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDirectionX() {
        return directionX;
    }

    public double getDirectionY() {
        return directionY;
    }

    public double getSpeed() {
        return speed;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }
}