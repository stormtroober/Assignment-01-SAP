package sap.ass01.hexagonal.application.entities;


public record EBikeDTO(String id, double x, double y, EBikeState state, int battery) {
}
