package sap.ass01.layered.persistence.dto;

import java.util.Date;
import java.util.Optional;

public record RideDTO(String id, UserDTO user, EBikeDTO EBike, Date startedDate, Optional<Date> endDate) {


}
