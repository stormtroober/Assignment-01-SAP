package sap.ass01.hexagonal.application.ports.entities;

import java.util.Date;
import java.util.Optional;

public record RideDTO(String id, EBikeDTO ebike, UserDTO user, Date startedDate, Optional<Date> endDate) {
}