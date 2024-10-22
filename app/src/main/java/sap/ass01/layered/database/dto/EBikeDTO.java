package sap.ass01.layered.database.dto;

public record EBikeDTO(String id, String state, double x, double y, double directionX, double directionY, double speed,
                       int batteryLevel) {
}