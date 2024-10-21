package sap.ass01.layered.database.dto;

import java.util.Date;
import java.util.Optional;

public record RideDTO(String id, UserDTO user, EBikeDTO bike, Date startedDate, Optional<Date> endDate) {


}
