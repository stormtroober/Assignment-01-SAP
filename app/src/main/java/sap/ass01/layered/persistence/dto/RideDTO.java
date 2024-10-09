package sap.ass01.layered.persistence.dto;

import java.util.Date;
import java.util.Optional;

public class RideDTO {
    private final String id;
    private final UserDTO user;
    private final EBikeDTO eBike;
    private final Date startedDate;
    private final Optional<Date> endDate;


    public RideDTO(final String id, final UserDTO user, final EBikeDTO eBike, final Date startedDate, final Optional<Date> endDate) {
        this.id = id;
        this.user = user;
        this.eBike = eBike;
        this.startedDate = startedDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public UserDTO getUser() {
        return user;
    }

    public EBikeDTO getEbike() {
        return eBike;
    }

    public Date getStartedDate() {
        return startedDate;
    }

    public Optional<Date> getEndDate() {
        return endDate;
    }


}
